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
		setContentView(R.layout.reg); // ���õ�ǰ��Ļ
		Button btnReg = (Button) findViewById(R.id.btnReg); // ���ע��Button��ť����
		btnReg.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				register();
			}
		});
		Button btnBack = (Button) findViewById(R.id.btnBack); // ��÷��ذ�ť����
		btnBack.setOnClickListener(new View.OnClickListener()
		{ // Ϊ���ذ�ť��Ӽ�����
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(RegActivity.this,
						LoginActivity.class); // ����Intent����
				startActivity(intent); // ����Activity
				finish();
			}
		});

	}

	// ���������ӷ�����������ע��
	public void register()
	{
		new Thread()
		{
			public void run()
			{
				Looper.prepare();
				// ����û���������ݲ�������֤
				EditText etName = (EditText) findViewById(R.id.etName); // ����ǳ�EditText����
				EditText etPwd1 = (EditText) findViewById(R.id.etPwd1); // �������EditText����
				EditText etPwd2 = (EditText) findViewById(R.id.etPwd2); // ���ȷ������EditText����
				EditText age1 = (EditText) findViewById(R.id.age); // �������EditText����

				String name = etName.getEditableText().toString().trim(); // ����ǳ�
				String pwd1 = etPwd1.getEditableText().toString().trim(); // �������
				String pwd2 = etPwd2.getEditableText().toString().trim(); // ���ȷ������
				String age = age1.getEditableText().toString().trim(); // �������
				System.out.println("name-->>" + name);
				System.out.println("pwd1-->>" + pwd1);
				System.out.println("pwd2-->>" + pwd2);
				if (name.equals("") || pwd1.equals("") || pwd2.equals("")
						|| age.equals(""))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegActivity.this);
					builder.setTitle("��ʾ");
					builder.setMessage("�뽫ע����Ϣ��д����");
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
				}

				if (!pwd1.equals(pwd2))
				{ // �ж���������������Ƿ�һ��
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegActivity.this);
					builder.setTitle("��ʾ");
					builder.setMessage("������������벻һ�£�");
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
//					age = "a";
				}

				String path = CommonUrl.REG_URL;
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", name);
				params.put("pswd", pwd1);
				params.put("age", age);
				String result = HttpUtils.sendHttpClientPost(path, params,
						"utf-8");
				// ת���������
				System.out.println("result-->>" + result);
				if (result.startsWith("<#REGIST_FAIL#>"))
				{ // ������ϢΪע��ʧ��

					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegActivity.this);
					builder.setTitle("��ʾ");
					builder.setMessage("ע��ʧ�ܣ��뷵������ע��");
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
				{ // ע��ɹ�
					Toast.makeText(RegActivity.this, "ע��ɹ�,���Ե�¼��", Toast.LENGTH_LONG)
							.show();
				}
				Looper.loop();
			}
		}.start();
	}

}