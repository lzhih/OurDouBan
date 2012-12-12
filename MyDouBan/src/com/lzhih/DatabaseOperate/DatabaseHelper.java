package com.lzhih.DatabaseOperate;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
	public static final String DATABASE_NAME="Books.db";
	
	 private final static int DATABASE_VERSION=3;
	 
	 private static DatabaseHelper helper = null;
	 
	 private static final AtomicInteger usageCounter = new AtomicInteger(0);
	 
	 private Dao<BookData, Integer> bookData;
	 
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1)
	{
		try
		{
			TableUtils.createTable(arg1, BookData.class);
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3)
	{
		try {
			
			TableUtils.dropTable(connectionSource, BookData.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(arg0, connectionSource);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	  public static synchronized DatabaseHelper getHelper(Context context) {
			if (helper == null) {
				helper = new DatabaseHelper(context);
			}
			usageCounter.incrementAndGet();
			return helper;
		}
	
	  public Dao<BookData, Integer> getBookDataDao()
	  {
		  if(bookData == null)
		  {
			  try
			{
				bookData = getDao(BookData.class);
				
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		  }
		return bookData;
	  }
	  
		public void close() {
			if (usageCounter.decrementAndGet() == 0) {
				super.close();
				bookData = null;
				helper = null;
			}
		}
}
