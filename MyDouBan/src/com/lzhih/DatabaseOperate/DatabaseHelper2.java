package com.lzhih.DatabaseOperate;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper2 extends OrmLiteSqliteOpenHelper {
	    private final static String DATABASE_NAME="groupinfo.db";
	    private final static int DATABASE_VERSION=3;
	    public Dao<UserData, Integer> UserInfoData;
		private static DatabaseHelper2 helper = null;
		private static final AtomicInteger usageCounter = new AtomicInteger(0);
	    public String content ;
	    
	    public DatabaseHelper2(Context context)
	    {
	        super(context, DATABASE_NAME,null, DATABASE_VERSION);
	    }
	    public static synchronized DatabaseHelper2 getHelper(Context context) {
			if (helper == null) {
				helper = new DatabaseHelper2(context);
			}
			usageCounter.incrementAndGet();
			return helper;
		}
	    
		@Override
		public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1)
		{
			try
			{
				Log.i("xxxxxxxxxxx", "onCreate2");
				TableUtils.createTable(arg1, UserData.class);
//				Dao<GroupInfoData, Integer> dao = getGroupInfoData();
//				GroupInfoData groupInfoData = new GroupInfoData("学生","5年","20人");
//				dao.create(groupInfoData);
//				groupInfoData = new GroupInfoData("老师","10年","10人");
//				dao.create(groupInfoData);
//				groupInfoData = new GroupInfoData("军人","5年","555人");
//				dao.create(groupInfoData);
//				groupInfoData = new GroupInfoData("演员","2年","800人");
//				dao.create(groupInfoData);
				
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		public Dao<UserData, Integer> getUserInfoData()
		{
			if(UserInfoData == null)
			{
				try
				{
					UserInfoData = getDao(UserData.class);
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return UserInfoData;
		}
		@Override
		public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1,
				int arg2, int arg3)
		{
			try {
				TableUtils.dropTable(connectionSource, UserData.class, true);
				// after we drop the old databases, we create the new ones
				onCreate(arg0, connectionSource);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}	 
		public void close() {
			if (usageCounter.decrementAndGet() == 0) {
				super.close();
				UserInfoData = null;
				helper = null;
			}
		}
}
