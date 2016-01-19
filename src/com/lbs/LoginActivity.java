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
				android.os.Process.killProcess(android.os.Process.myPid()); // �������̣��˳�����
			}
		});
	}

	// ���������ӷ��������е�¼
	public void login()
	{
		new Thread()
		{
			public void run()
			{
				Looper.prepare();

				EditText etUid = (EditText) findViewById(R.id.etUid); // ����ʺ�EditText
				EditText etPwd = (EditText) findViewById(R.id.etPwd); // �������EditText
				String uid = etUid.getEditableText().toString().trim(); // ���������ʺ�
				String pwd = etPwd.getEditableText().toString().trim(); // ������������
				if (uid.equals("") || pwd.equals(""))
				{ // �ж������Ƿ�Ϊ��
					Toast.makeText(LoginActivity.this, "�������ʺŻ�����!",
							Toast.LENGTH_SHORT).show();// �����ʾ��Ϣ
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
				// ת���������
				System.out.println("result-->>" + result);
				System.out.println("UID-->>" + UID);
				if (result.startsWith("<#LOGIN_FAIL#>"))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(
							LoginActivity.this);
					builder.setTitle("��ʾ");
					builder.setMessage("��¼ʧ�ܣ��뷵�����µ�¼");
					builder.setIcon(R.drawable.ic_launcher);
					builder.setNegativeButton("ȷ��", new OnClickListener()
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
					startActivity(intent); // ��������Activity
					finish();
				}

				Looper.loop();
			}
		}.start();
	}

	// ���������û���id���������Preferences
	public void rememberMe(String uid, String pwd)
	{
		SharedPreferences sp = getPreferences(MODE_PRIVATE); // ���Preferences
		SharedPreferences.Editor editor = sp.edit(); // ���Editor
		editor.putString("uid", uid); // ���û�������Preferences
		editor.putString("pwd", pwd); // ���������Preferences
		editor.commit();
	}

	// ��������Preferences�ж�ȡ�û���������
	public void checkIfRemember()
	{
		SharedPreferences sp = getPreferences(MODE_PRIVATE); // ���Preferences
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