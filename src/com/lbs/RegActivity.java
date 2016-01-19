package com.lbs;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg); // 设置当前屏幕
		Button btnReg = (Button) findViewById(R.id.btnReg); // 获得注册Button按钮对象
		btnReg.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				register();
			}
		});
		Button btnBack = (Button) findViewById(R.id.btnBack); // 获得返回按钮对象
		btnBack.setOnClickListener(new View.OnClickListener()
		{ // 为返回按钮添加监听器
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(RegActivity.this,
						LoginActivity.class); // 创建Intent对象
				startActivity(intent); // 启动Activity
				finish();
			}
		});

	}

	// 方法：连接服务器，进行注册
	public void register()
	{
		new Thread()
		{
			public void run()
			{
				Looper.prepare();
				// 获得用户输入的数据并进行验证
				EditText etName = (EditText) findViewById(R.id.etName); // 获得昵称EditText对象
				EditText etPwd1 = (EditText) findViewById(R.id.etPwd1); // 获得密码EditText对象
				EditText etPwd2 = (EditText) findViewById(R.id.etPwd2); // 获得确认密码EditText对象
				EditText age1 = (EditText) findViewById(R.id.age); // 获得年龄EditText对象

				String name = etName.getEditableText().toString().trim(); // 获得昵称
				String pwd1 = etPwd1.getEditableText().toString().trim(); // 获得密码
				String pwd2 = etPwd2.getEditableText().toString().trim(); // 获得确认密码
				String age = age1.getEditableText().toString().trim(); // 获得年龄
				System.out.println("name-->>" + name);
				System.out.println("pwd1-->>" + pwd1);
				System.out.println("pwd2-->>" + pwd2);
				if (name.equals("") || pwd1.equals("") || pwd2.equals("")
						|| age.equals(""))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegActivity.this);
					builder.setTitle("提示");
					builder.setMessage("请将注册信息填写完整");
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
				}

				if (!pwd1.equals(pwd2))
				{ // 判断两次输入的密码是否一致
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegActivity.this);
					builder.setTitle("提示");
					builder.setMessage("两次输入的密码不一致！");
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
//					age = "a";
				}

				String path = CommonUrl.REG_URL;
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", name);
				params.put("pswd", pwd1);
				params.put("age", age);
				String result = HttpUtils.sendHttpClientPost(path, params,
						"utf-8");
				// 转到功能面板
				System.out.println("result-->>" + result);
				if (result.startsWith("<#REGIST_FAIL#>"))
				{ // 返回信息为注册失败

					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegActivity.this);
					builder.setTitle("提示");
					builder.setMessage("注册失败，请返回重新注册");
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
				{ // 注册成功
					Toast.makeText(RegActivity.this, "注册成功,可以登录！", Toast.LENGTH_LONG)
							.show();
				}
				Looper.loop();
			}
		}.start();
	}

}