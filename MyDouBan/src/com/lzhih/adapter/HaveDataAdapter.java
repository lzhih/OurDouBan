package com.lzhih.adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.example.jsontest.R;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.lzhih.DatabaseOperate.BookData;
import com.lzhih.DatabaseOperate.DatabaseHelper;
import com.lzhih.DatabaseOperate.UserData;

public class HaveDataAdapter extends BaseAdapter
{
	private Context context;
	private String name;
	private AQuery aq;
	private View view;
	private UserData userData;
	private Dao<BookData, Integer> dao;
	private DatabaseHelper databaseHelper = null;
	private List<BookData> bookList_2;
	private int i = 0;

	public HaveDataAdapter(String name, Context context, int i)
	{
		this.context = context;
		this.name = name;
		dao = getHelper1().getBookDataDao();
		userData = new UserData();
		try
		{
			bookList_2 = dao.queryForAll();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getCount()
	{
		return da().size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return da().size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		view = LayoutInflater.from(context).inflate(
				R.layout.my_collection_book, null);
		try
		{
			
			GenericRawResults<String[]> rawResults =
					dao.queryRaw(
					"select * from BookData where userID="+name);
			List<String[]> results = rawResults.getResults();
			String[] resultArray = results.get(position);
			Log.e("Select", "" + resultArray[0]);
			Log.e("size", "" +da().size());
			aq = new AQuery(view);
			List<BookData> bookList = dao.queryForAll();
			Log.e("size22", "" + position);
			getHelper1().getBookDataDao().refresh(userData.getUserInfo());
			aq.id(R.id.titile).text(resultArray[7]);
			aq.id(R.id.author_intro).text(resultArray[6]);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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

	public List<Integer> da()
	{
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < bookList_2.size(); i++)
		{
			if (bookList_2.get(i).getUserID().equals(name))
			{
				list.add(i);
			}
		}
		return list;
	}
}
