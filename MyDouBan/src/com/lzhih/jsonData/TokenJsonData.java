package com.lzhih.jsonData;

public class TokenJsonData
{
	private String access_token;
	private String refresh_token;
	private String douban_user_id;
	
	public String getDouban_user_id()
	{
		return douban_user_id;
	}
	public void setDouban_user_id(String douban_user_id)
	{
		this.douban_user_id = douban_user_id;
	}
	public String getAccess_token()
	{
		return access_token;
	}
	public void setAccess_token(String access_token)
	{
		this.access_token = access_token;
	}
	public String getRefresh_token()
	{
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token)
	{
		this.refresh_token = refresh_token;
	}
	
}
