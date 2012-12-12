package com.example.activitys;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.jsontest.R;
import com.j256.ormlite.dao.Dao;
import com.lzhih.DatabaseOperate.BookData;
import com.lzhih.DatabaseOperate.DatabaseHelper;
import com.lzhih.jsonData.Book;

public class MainActivity extends Activity
{
	private AQuery aq;
	private String str;
	private Book book;
	private BookData bookData;
	private String image_url = null;
	private DatabaseHelper databaseHelper = null;
	private Dao<BookData, Integer> dao;
	private static int id;
	private boolean isCache = false;//是否数据已经保存在数据库
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		aq = new AQuery(this);
		dao = getHelper1().getBookDataDao();
		try
		{
			AsyncJson();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void AsyncJson() throws SQLException
	{
		url = "https://api.douban.com/v2/book/1120554";
		Dao<BookData, Integer> booksDao = getHelper1().getBookDataDao();
		List<BookData> list = booksDao.queryForAll();
		Log.i("xxxx", "" + list.size());
		//遍历数据库数据,对比url
		for (BookData bookData : list)
		{
			if (bookData.getUrl().equals(url))
			{
				Log.i("xxxx", "DD");
				isCache = true;
				this.bookData = bookData;
//				id = bookData.getId();;
			}
		}
		/**
		 * isCache == true 数据在数据库中,直接读取
		 */
		if (isCache == true)
		{
			
			Log.i("xxxx", "AA");
			aq.id(R.id.title).text(bookData.getTitle());
			aq.id(R.id.author).text(bookData.getTitle());
//			aq.id(R.id.publisher).text(bookData.getPublisher());
//			aq.id(R.id.pages).text(bookData.getPages());
			aq.id(R.id.summary).text(bookData.getSummary());
//			aq.id(R.id.translator).text(bookData.getTranslator());
//			aq.id(R.id.pubdate).text(bookData.getPubdate());
			aq.id(R.id.Image).progress(R.id.Progress)
					.image(bookData.getImages(), true, true, 200, 0);
		} else
		{
			aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>()
			{
				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status)
				{
					// aq.id(R.id.textView).text(object.toString());
					str = object.toString();
					book = JSON.parseObject(str, Book.class);
					// aq.id(R.id.title).text(book.getPrice());
					aq.id(R.id.title).text(book.getTitle());
					aq.id(R.id.author).text(book.getAuthor());
//					aq.id(R.id.publisher).text(book.getPublisher());
//					aq.id(R.id.pages).text(book.getPages());
					aq.id(R.id.summary).text(book.getSummary());
//					aq.id(R.id.translator).text(book.getTranslator());
//					aq.id(R.id.pubdate).text(book.getPubdate());
					image_url = book.getImages().getLarge();
					aq.id(R.id.Image).progress(R.id.Progress)
							.image(image_url, true, true, 200, 0);
					InsertInDataBase();
				}
			});
		}

	}

	private DatabaseHelper getHelper1()
	{
		if (databaseHelper == null)
		{
			databaseHelper = DatabaseHelper.getHelper(this);
		}
		return databaseHelper;
	}

	
	/**
	 * 数据直接插入数据库
	 */
	public void InsertInDataBase()
	{
		try
		{
			// dao.queryForAll();
			Book books = new Book(book.getAuthor(), book.getSummary(),
					book.getTitle(), book.getPrice(), book.getPublisher(),
					book.getPubdate(), book.getTranslator(), book.getPages());
			BookData bookss = new BookData();
			bookss.setAuthor(book.getAuthor());
			bookss.setSummary(book.getSummary());
			bookss.setTitle(book.getTitle());
			bookss.setPrice(book.getPrice());
			bookss.setPublisher(book.getPublisher());
			bookss.setPubdate(book.getPubdate());
			bookss.setTranslator(book.getTranslator());
			bookss.setPages(book.getPages());
			bookss.setImages(image_url);
			bookss.setUrl(url);
			dao.create(bookss);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
