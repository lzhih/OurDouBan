package com.lzhih.jsonData;

import java.util.List;

public class MyCollections
{
	private String total;
	private String start;
	private String count;
	private List<MyCollectionBook> collections;
	
	
	public List<MyCollectionBook> getCollections()
	{
		return collections;
	}
	public void setCollections(List<MyCollectionBook> collections)
	{
		this.collections = collections;
	}
	public String getCount()
	{
		return count;
	}
	public void setCount(String count)
	{
		this.count = count;
	}

	public String getTotal()
	{
		return total;
	}
	public void setTotal(String total)
	{
		this.total = total;
	}
	public String getStart()
	{
		return start;
	}
	public void setStart(String start)
	{
		this.start = start;
	}

}
