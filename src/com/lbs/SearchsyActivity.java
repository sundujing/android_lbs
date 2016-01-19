package com.lbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SearchsyActivity extends Activity
{
	private String UID;
	private MyApp myApp;
	private TextView dy;
	private TextView yl;
	private TextView js;
	private TextView jd;
	private TextView sc;
	private TextView hs;
	private String scategory;
	 private long mExitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchsy);
		myApp = (MyApp) getApplication();
		UID = myApp.getUID();
		System.out.println("UID SEARCHsy-->>" + UID);
		dy=(TextView)findViewById(R.id.dy);
		dy.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="��ӰԺ"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		yl=(TextView)findViewById(R.id.yl);
		yl.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="���ֳ�"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		js=(TextView)findViewById(R.id.js);
		js.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="����"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		jd=(TextView)findViewById(R.id.jd);
		jd.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="����"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		sc=(TextView)findViewById(R.id.sc);
		sc.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="�̳�"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		hs=(TextView)findViewById(R.id.hs);
		hs.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="����"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		
	}
	// ���ؼ�����
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if ((System.currentTimeMillis() - mExitTime) > 2000)
			{
				Toast.makeText(this, "�ٰ�һ�η��ؼ��˳�����", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else
			{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
