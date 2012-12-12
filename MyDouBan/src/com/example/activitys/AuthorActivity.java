package com.example.activitys;

import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import auth.DoubanHandle;

import com.alibaba.fastjson.JSON;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.jsontest.R;
import com.j256.ormlite.dao.Dao;
import com.lzhih.DatabaseOperate.BookData;
import com.lzhih.DatabaseOperate.DatabaseHelper;
import com.lzhih.DatabaseOperate.DatabaseHelper2;
import com.lzhih.DatabaseOperate.UserData;
import com.lzhih.jsonData.MyInfo;
import com.nineoldandroids.animation.ObjectAnimator;

public class AuthorActivity extends Activity implements OnClickListener
{
	AQuery doubanQuery;
	public static int k = 0;
	private SharedPreferences sp;
	private static int isOpen = 0;
	public static MyInfo myInfo;
	private UserData user;
	private UserData userData;
	private Dao<BookData, Integer> dao;
	private Dao<UserData, Integer> dao2;
	private DatabaseHelper databaseHelper = null;
	private DatabaseHelper2 databaseHelper2 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.author);
		doubanQuery = new AQuery(this);
		doubanQuery.id(R.id.button1).clicked(this);
		doubanQuery.id(R.id.button2).clicked(this);
		doubanQuery.id(R.id.myInfoImage).clicked(this);
		doubanQuery.id(R.id.xianshi).clicked(this);
		databaseHelper2 = new DatabaseHelper2(AuthorActivity.this);
		dao = getHelper1().getBookDataDao();
		dao2 = getHelper2().getUserInfoData();
		myInfo = new MyInfo();
		user = new UserData();
		IsNeedGetauth();
	}
	public void getAuth()
	{
		SharedPreferences sp = getSharedPreferences("tokenData", MODE_PRIVATE);
		String accessToken = sp.getString("token", null);
		DoubanHandle dh = new DoubanHandle(accessToken, this);
		String url = "https://api.douban.com/v2/user/~me";
		doubanQuery.auth(dh).ajax(url, JSONObject.class,
				new AjaxCallback<JSONObject>()
				{
					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status)
					{
						super.callback(url, object, status);
						switch (status.getCode())
						{
						case HttpURLConnection.HTTP_BAD_REQUEST:
							Toast.makeText(getApplication(), status.getError(),
									Toast.LENGTH_LONG).show();
							break;
						case HttpURLConnection.HTTP_OK:
							MyInfoJson(object);
						default:
							break;
						}
					}
				});
	}

	public void MyInfoJson(JSONObject jsonObject)
	{
		
		String myInfoStr = jsonObject.toString();
		myInfo = JSON.parseObject(myInfoStr, MyInfo.class);
		doubanQuery.id(R.id.name).text(myInfo.getName());
//		doubanQuery.id(R.id.name).text(user.getName());
		doubanQuery.id(R.id.myInfoImage).progress(R.id.myInfoProgress)
				.image(myInfo.getAvatar(), true, true, 200, 0);
		doubanQuery
				.id(R.id.BigImage)
				.progress(R.id.BigProgress)
				.image("http://img3.douban.com/lpic/s1316434.jpg", true, true,
						200, 0);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.button1:
			Intent intent = new Intent(getApplication(),
					MyCollectionActivity.class);
			startActivity(intent);
			break;
		case R.id.button2:
			SharedPreferences sp = getSharedPreferences("tokenData",
					MODE_PRIVATE);
			sp.edit().clear().commit();
//			DeleteAllData();
			break;
		case R.id.myInfoImage:
			if(isOpen == 0)
			{
				ObjectAnimator
					.ofFloat(doubanQuery.id(R.id.xianshi).getView(),
							"translationX", 0, 230).setDuration(500).start();
				isOpen = 1;
			}
			else
			{
				ObjectAnimator
				.ofFloat(doubanQuery.id(R.id.xianshi).getView(),
						"translationX", 230, 0).setDuration(500).start();
				isOpen = 0;
			}
			break;
		case R.id.xianshi:
			if(isOpen == 1)
			{
				ObjectAnimator
				.ofFloat(doubanQuery.id(R.id.xianshi).getView(),
						"translationX", 230, 0).setDuration(500).start();
				isOpen = 0;
			}
			break;
		default:
			break;
		}
	}

	public void IsNeedGetauth()
	{
		sp = getSharedPreferences("tokenData", MODE_PRIVATE);
		if (sp.getString("token", null) == null)
		{
			AlertDialog.Builder builder = new Builder(AuthorActivity.this);
			builder.setMessage("尚未授权,前去授权?");
			builder.setTitle("提示");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							// sp.edit().putString("token", "..").commit();
							getAuth();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							finish();
						}
					});
			builder.show();
		} else
		{
			getAuth();
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

	private DatabaseHelper2 getHelper2()
	{
		if (databaseHelper2 == null)
		{
			databaseHelper2 = DatabaseHelper2.getHelper(this);
		}
		return databaseHelper2;
	}
	
	
//	public void DeleteAllData()
//	{
//		try
//		{
//			List<BookData> bookList;
//			bookList = dao.queryForAll();
//			List<UserData> userList = dao2.queryForAll();
//			Log.e("Num", "book:"+bookList.size());
//			Log.e("Num", "userList:"+userList.size());
//			if(bookList.size() != 0)
//			{
//				for(int i = 1 ;i<=bookList.size() ;i++)
//				{
//					dao.deleteById(i);
//				}
//			}
//			if(userList.size() != 0)
//			{
//				for(int i = 1 ; i<=userList.size() ;i++)
//				{
//					dao2.deleteById(i);
//				}
//			}
//		} catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
