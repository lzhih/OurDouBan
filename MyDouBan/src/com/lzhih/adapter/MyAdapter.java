package com.lzhih.adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.example.activitys.AuthorActivity;
import com.example.jsontest.R;
import com.j256.ormlite.dao.Dao;
import com.lzhih.DatabaseOperate.BookData;
import com.lzhih.DatabaseOperate.DatabaseHelper;
import com.lzhih.DatabaseOperate.DatabaseHelper2;
import com.lzhih.DatabaseOperate.UserData;
import com.lzhih.jsonData.MyCollectionBook;
import com.lzhih.jsonData.MyInfo;

public class MyAdapter extends BaseAdapter
{
	private Context context;
	private List<MyCollectionBook> list;
	private AQuery aq;
	private View view;
	private BookData books;
	private UserData userData;
	private Dao<BookData, Integer> dao;
	private Dao<UserData, Integer> dao2;
	private DatabaseHelper databaseHelper = null;
	private DatabaseHelper2 databaseHelper2 = null;
	private List<Integer> isInsert = new ArrayList<Integer>();

	public MyAdapter(List<MyCollectionBook> list, Context context)
	{
		this.context = context;
		this.list = list;
		dao = getHelper1().getBookDataDao();
		dao2 = getHelper2().getUserInfoData();
		userData = new UserData();
	}

	@Override
	public int getCount()
	{
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	
		view = LayoutInflater.from(context).inflate(
				R.layout.my_collection_book, null);
		try
		{
			view.setBackgroundColor(Color.WHITE);
			aq = new AQuery(view);
			Log.i("MyAdapter",""+position);
			
			MyInfo myInfo = AuthorActivity.myInfo;
			dao.queryForAll();
				aq.id(R.id.titile)
						.text(list.get(position).getBook().getTitle());
				aq.id(R.id.author_intro).text(
						list.get(position).getBook().getAuthor_intro());
				aq.id(R.id.myInfoImage)
						.progress(R.id.myInfoProgress)
						.image(list.get(position).getBook().getImages()
								.getLarge(), true, true, 200, 0);
				if(!isInsert.contains(position))
				{
					Log.e("IsInsert:",""+!isInsert.contains(position));
					Log.e("IsInsert:",""+position);
					isInsert.add(position);
					books = new BookData(list.get(position).getBook().getAuthor(),
							list.get(position).getBook().getAuthor_intro(), list
									.get(position).getBook().getTitle(), list
									.get(position).getBook().getPrice(), list
									.get(position).getBook().getPublisher(), list
									.get(position).getUpdated(), list.get(position)
									.getBook().getTranslator(), list.get(position)
									.getBook().getPages(), myInfo.getId());

					dao.create(books);
					userData.setUserInfo(books);
					userData.setName(myInfo.getName());
					userData.setUserId(myInfo.getId());
					dao2.create(userData);
			
				}
//			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		// Log.i("xxx", list.get(position).getBook().getPrice());
		// aq.id(R.id.status).text(list.get(position).getStatus());
		// aq.id(R.id.updated).text(list.get(position).getUpdated());
		// aq.id(R.id.publisher).text(list.get(position).getBook().getPublisher());
		// aq.id(R.id.pages).text(list.get(position).getBook().getPages());
		// aq.id(R.id.price).text(list.get(position).getBook().getPrice());
		// aq.id(R.id.author).text(list.get(position).getBook().getAuthor());
		return view;
	}

	private DatabaseHelper getHelper1()
	{
		if (databaseHelper == null)
		{
			databaseHelper = DatabaseHelper.getHelper(context);
		}
		return databaseHelper;
	}
	private DatabaseHelper2 getHelper2()
	{
		if (databaseHelper2 == null)
		{
			databaseHelper2 = DatabaseHelper2.getHelper(context);
		}
		return databaseHelper2;
	}
}
