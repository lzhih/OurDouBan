package com.lzhih.jsonData;

public class Tag
{
	private int count ;
	private String name;
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "count:"+count+"name:"+name;
	}
	
}
