package com.lbs;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity
{

	private Button btnLogin;
	private Button btnReg;
	private MyApp myApp;
	
	private CheckBox cbRemember;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		cbRemember = (CheckBox)findViewById(R.id.cbRemember);
		checkIfRemember();
		
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		
		btnLogin.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				login();
			}
		});
		btnReg = (Button) findViewById(R.id.btnReg);
		btnReg.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(LoginActivity.this,
						com.lbs.RegActivity.class);
				startActivity(intent);
				finish();
			}
		});
		ImageButton ibExit = (ImageButton) findViewById(R.id.ibExit);
		ibExit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				android.os.Process.killProcess(android.os.Process.myPid()); // 结束进程，退出程序
			}
		});
	}

	// 方法：连接服务器进行登录
	public void login()
	{
		new Thread()
		{
			public void run()
			{
				Looper.prepare();

				EditText etUid = (EditText) findViewById(R.id.etUid); // 获得帐号EditText
				EditText etPwd = (EditText) findViewById(R.id.etPwd); // 获得密码EditText
				String uid = etUid.getEditableText().toString().trim(); // 获得输入的帐号
				String pwd = etPwd.getEditableText().toString().trim(); // 获得输入的密码
				if (uid.equals("") || pwd.equals(""))
				{ // 判断输入是否为空
					Toast.makeText(LoginActivity.this, "请输入帐号或密码!",
							Toast.LENGTH_SHORT).show();// 输出提示消息
					return;
				}

				String path = CommonUrl.LOGIN_URL;
				String path1 = CommonUrl.LOGIN_URL1;
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", uid);
				params.put("pswd", pwd);
				String result = HttpUtils.sendHttpClientPost(path, params,
						"utf-8");
				String UID = HttpUtils.sendHttpClientPost(path1, params,
						"utf-8");
				// 转到功能面板
				System.out.println("result-->>" + result);
				System.out.println("UID-->>" + UID);
				if (result.startsWith("<#LOGIN_FAIL#>"))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(
							LoginActivity.this);
					builder.setTitle("提示");
					builder.setMessage("登录失败，请返回重新登录");
					builder.setIcon(R.drawable.ic_launcher);
					builder.setNegativeButton("确定", new OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{

						}
					});
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
				} else
				{
					System.out.println("UID--->" + UID);
					myApp = (MyApp) getApplication();
					myApp.setUID(UID);
					myApp.setusername(uid);
					if(cbRemember.isChecked())
					{
						rememberMe( uid,  pwd);
					}
					
					Intent intent = new Intent(LoginActivity.this,
							SearchActivity.class);
					System.out.println("UID-->>!" + UID);
					startActivity(intent); // 启动功能Activity
					finish();
				}

				Looper.loop();
			}
		}.start();
	}

	// 方法：将用户的id和密码存入Preferences
	public void rememberMe(String uid, String pwd)
	{
		SharedPreferences sp = getPreferences(MODE_PRIVATE); // 获得Preferences
		SharedPreferences.Editor editor = sp.edit(); // 获得Editor
		editor.putString("uid", uid); // 将用户名存入Preferences
		editor.putString("pwd", pwd); // 将密码存入Preferences
		editor.commit();
	}

	// 方法：从Preferences中读取用户名和密码
	public void checkIfRemember()
	{
		SharedPreferences sp = getPreferences(MODE_PRIVATE); // 获得Preferences
		String uid = sp.getString("uid", null);
		String pwd = sp.getString("pwd", null);
		if (uid != null && pwd != null)
		{
			EditText etUid = (EditText) findViewById(R.id.etUid);
			EditText etPwd = (EditText) findViewById(R.id.etPwd);
			cbRemember = (CheckBox) findViewById(R.id.cbRemember);
			etUid.setText(uid);
			etPwd.setText(pwd);
			cbRemember.setChecked(true);
		}
	}

}