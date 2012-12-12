package com.example.activitys;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.aphidmobile.flip.FlipViewController;
import com.example.jsontest.R;
import com.j256.ormlite.dao.Dao;
import com.lzhih.DatabaseOperate.DatabaseHelper;
import com.lzhih.DatabaseOperate.DatabaseHelper2;
import com.lzhih.DatabaseOperate.UserData;
import com.lzhih.adapter.HaveDataAdapter;
import com.lzhih.adapter.MyAdapter;
import com.lzhih.jsonData.MyCollectionBook;
import com.lzhih.jsonData.MyCollections;

public class MyCollectionActivity extends Activity
{
	private AQuery aq ;
	private String str;
	private ListView listView ;
	private List<MyCollectionBook> list;
	public FlipViewController flipView;
	private UserData userData;
	private Dao<UserData, Integer> dao2;
	private DatabaseHelper2 databaseHelper2 = null;
	private DatabaseHelper databaseHelper = null;
	private boolean isCache = false;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.collection_list);
		aq = new AQuery(this);
		flipView = new FlipViewController(this);
//		listView =(ListView) findViewById(R.id.list);
		userData = new UserData();
		dao2 = getHelper2().getUserInfoData();
		sp = getSharedPreferences("tokenData", MODE_PRIVATE);
		Query();
		AsnyInfo();
	}
	
	public void AsnyInfo()
	{
		Log.i("Is - > is",""+isCache);
		if(isCache)
		{
			HaveDataAdapter adapter = new HaveDataAdapter(sp.getString("user_id", null),MyCollectionActivity.this,10);
			flipView.setAdapter(adapter);
			setContentView(flipView);
		}
		else
		{
		String url = "https://api.douban.com/v2/book/user/"+sp.getString("user_id", null)+"/collections";
//		url = "https://api.douban.com/v2/book/1120550";
//		String accessToken = "46fd94db4ce49f7ad4f8be91e6adde6e";
		aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>()
				{
					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status)
					{
						MyCollections my = new MyCollections();
						str = object.toString();
						System.out.println(str);
						my = JSON.parseObject(str, MyCollections.class);
						list =my.getCollections();
						MyAdapter adapter = new MyAdapter(list,MyCollectionActivity.this);
//						listView.setAdapter(adapter);
						flipView.setAdapter(adapter);
						setContentView(flipView);
					}
				});
		}
	}
	public void Query()
	{
		try
		{
			List<UserData> userList = dao2.queryForAll();
			for(UserData user : userList)
			{
				if (user.getUserId().equals(sp.getString("user_id", null)))
				{
//					getHelper1().getBookDataDao().refresh(user.getUserInfo());
//					Log.i("title - > is",user.getUserInfo().getTitle());
//					Log.i("title - > is",user.getUserInfo().getAuthor());
					isCache = true;
					userData = user;
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private DatabaseHelper getHelper1() {
		if (databaseHelper == null) {
			databaseHelper = DatabaseHelper.getHelper(this);
		}
		return databaseHelper;
	}
	private DatabaseHelper2 getHelper2() {
		if (databaseHelper2 == null) {
			databaseHelper2 = DatabaseHelper2.getHelper(this);
		}
		return databaseHelper2;
	}
}
