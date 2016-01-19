package com.lbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SearchscActivity extends Activity
{
	private String UID;
	private MyApp myApp;
	private TextView c;
	private TextView y;
	private TextView s;
//	private TextView z;
	private TextView x;
	private TextView k;
	private TextView d;
	private String scategory;
	 private long mExitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchsc);
		myApp = (MyApp) getApplication();
		UID = myApp.getUID();
		System.out.println("UID SEARCHsc-->>" + UID);

		c=(TextView)findViewById(R.id.c);
		c.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="����"; 
				Intent intent = new Intent(SearchscActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		y=(TextView)findViewById(R.id.y);
		y.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="����"; 
				Intent intent = new Intent(SearchscActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		
		s=(TextView)findViewById(R.id.s);
		s.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="�ղ�"; 
				Intent intent = new Intent(SearchscActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		
		x=(TextView)findViewById(R.id.x);
		x.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="���"; 
				Intent intent = new Intent(SearchscActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});

		k=(TextView)findViewById(R.id.k);
		k.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="���"; 
				Intent intent = new Intent(SearchscActivity.this,
						ProductListActivity.class); // ����Intent����
				intent.putExtra("scategory", scategory);
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		
		d=(TextView)findViewById(R.id.d);
		d.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="������"; 
				Intent intent = new Intent(SearchscActivity.this,
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
