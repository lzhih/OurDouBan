package com.lzhih.DatabaseOperate;

import java.util.List;

import com.j256.ormlite.field.DatabaseField;

public class UserData
{
	@DatabaseField(generatedId = true)
	int id ;
	
	@DatabaseField(unique=true)
	String userId;

	@DatabaseField
	String name;
	
	@DatabaseField(canBeNull = false, foreign = true)
	BookData userInfo;

	public BookData getUserInfo()
	{
		return userInfo;
	}

	public void setUserInfo(BookData userInfo)
	{
		this.userInfo = userInfo;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
