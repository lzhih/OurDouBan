package com.example.activitys;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.jsontest.R;
import com.lzhih.jsonData.TokenJsonData;

public class AccountAuthActivity extends Activity
{
	AQuery doubanQuery;
	private WebView webView;
	private String code;
//	public static String token;
//	public static String refreshToken;
	private TokenJsonData tokenData;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acount_author);
		doubanQuery = new AQuery(this);
		
		sp = getSharedPreferences("tokenData", MODE_PRIVATE);
		editor = sp.edit();
		webView = (WebView) findViewById(R.id.webView);
		
		webView.setWebViewClient(new WebViewClient() {
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        return true;
		    }
		    @Override
		    public void onPageStarted(WebView view, String url, Bitmap favicon) {
		        Log.e("xx", url);
		    
		        //这儿可以截获网页的URL，可以都URL进行分析。
		        //本例子之中是分析从通过RenRen登录成功后返回的access_token.
		        if (url.contains("http://book.douban.com/?code=")) {
		            int start = url.indexOf("code=") + "code=".length();
		            int end = url.length();
		            code = url.substring(start, end);
//		            String tokenStr = "https://www.douban.com/service/auth2/token?client_id=04e2213d57cea3570ea663b8076bea05&client_secret=06c22cd45bdf9eca&redirect_uri=http://book.douban.com/&grant_type=authorization_code&code="+code;
		            GetToken();
		            Log.e("xx", code);
		            Intent intent = new Intent(AccountAuthActivity.this,AuthorActivity.class);
		            startActivity(intent);
		        }
		        super.onPageStarted(view, url, favicon);
		    }
		});
		webView.loadUrl("https://www.douban.com/service/auth2/auth?client_id=04e2213d57cea3570ea663b8076bea05&redirect_uri=http://book.douban.com/&response_type=code&scope=shuo_basic_r,shuo_basic_w,douban_basic_common");
	}
	
	public void GetToken()
	{
		 String newUrl = "https://www.douban.com/service/auth2/token";
         Map<String, String> params = new HashMap<String, String>();
         params.put("client_id", "04e2213d57cea3570ea663b8076bea05");
         params.put("client_secret", "06c22cd45bdf9eca");
         params.put("redirect_uri", "http://book.douban.com/");
         params.put("grant_type", "authorization_code");
         params.put("code", code);
          doubanQuery.ajax(newUrl, params, JSONObject.class, new AjaxCallback<JSONObject>()
			{
				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status)
				{
					Log.i("xx哦", ""+object.toString());
					String str = object.toString();
					tokenData = JSON.parseObject(str, TokenJsonData.class);
					editor.putString("token",tokenData.getAccess_token());
					editor.putString("refreshToken", tokenData.getAccess_token());
					editor.putString("user_id", tokenData.getDouban_user_id());
					editor.commit();
//					token = tokenData.getAccess_token();
//					refreshToken = tokenData.getRefresh_token();
					Log.e("Token",tokenData.getAccess_token());
					Log.e("Refresh_token",tokenData.getRefresh_token());
//					GetNewToken();
				}
			});
	}
	
	//token过期后用refreshToken换取新的token;
	public void GetNewToken()
	{
		  sp = getSharedPreferences("tokenData", MODE_PRIVATE);
		  String newUrl = "https://www.douban.com/service/auth2/token";
          Map<String, String> params = new HashMap<String, String>();
          params.put("client_id", "04e2213d57cea3570ea663b8076bea05");
          params.put("client_secret", "06c22cd45bdf9eca");
          params.put("redirect_uri", "http://book.douban.com/");
          params.put("grant_type", "refresh_token");
          params.put("refresh_token",sp.getString("refreshToken", null) );
           doubanQuery.ajax(newUrl, params, JSONObject.class, new AjaxCallback<JSONObject>()
			{
				@Override
				public void callback(String url, JSONObject object,
						AjaxStatus status)
				{
					Log.i("xx哦", ""+object.toString());
					String str = object.toString();
					tokenData = JSON.parseObject(str, TokenJsonData.class);
					sp.edit().clear();
					editor.putString("token",tokenData.getAccess_token());
					editor.putString("refreshToken", tokenData.getAccess_token());
					editor.putString("user_id", tokenData.getDouban_user_id());
					editor.commit();
//					token = tokenData.getAccess_token();
//					refreshToken = tokenData.getRefresh_token();
					Log.e("new_Token",tokenData.getAccess_token());
					Log.e("new_Refresh_token",tokenData.getRefresh_token());
				}
			});
       }
}
