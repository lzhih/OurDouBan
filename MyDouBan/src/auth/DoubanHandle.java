package auth;

import org.apache.http.HttpRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.androidquery.auth.AccountHandle;
import com.androidquery.callback.AbstractAjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.activitys.AccountAuthActivity;
import com.example.activitys.AuthorActivity;

public class DoubanHandle extends AccountHandle {
	
	private String accessToken;
	public static int fork = 0;
	private String taccessToken;
	private Context c;
	private SharedPreferences sp ;
	private AccountAuthActivity accountAuthActivity = new AccountAuthActivity();
	public DoubanHandle(String accessToken,Context c){
//		this.accessToken = null;
		this.taccessToken = accessToken;
		this.c =c;
	}
	@Override
	public boolean authenticated() {
		// TODO Auto-generated method stub
		sp = c.getSharedPreferences("tokenData", c.MODE_PRIVATE);
		Log.d("handle","authenticated"+( fork++));
		
		return sp.getString("token", null) != null;
//				accessToken!=null;
	}
	
	@Override
	public void applyToken(AbstractAjaxCallback<?, ?> cb, HttpRequest request) {
		Log.d("handle","applyToken"+( fork++));
		request.addHeader("Authorization", "Bearer "+sp.getString("token", null));
	}
	@Override
	protected void auth() {
		Log.d("handle","auth"+( fork++));
		// TODO Auto-generated method stub
		accessToken = taccessToken;
		success(c);
			Intent intent = new Intent(c,AccountAuthActivity.class);
			c.startActivity(intent);
	}

	@Override
	public boolean expired(AbstractAjaxCallback<?, ?> cb, AjaxStatus status) {
		Log.d("handle","expired"+( fork++));
		// TODO Auto-generated method stub
		//判断状态码是否需要重新授权
		int code = status.getCode();
		Log.d("handle", "status -->" + code);
		return code ==401 || code ==403;
	}

	@Override
	public boolean reauth(AbstractAjaxCallback<?, ?> cb) {
		Log.d("handle","reauth"+( fork++));
		// TODO Auto-generated method stub
		accountAuthActivity.GetNewToken();
		accessToken = sp.getString("token", null);
		return true;
	}



}
