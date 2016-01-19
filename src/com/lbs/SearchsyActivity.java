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
				scategory="电影院"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // 创建Intent对象
				intent.putExtra("scategory", scategory);
				startActivity(intent); // 启动Activity
				finish();
				
			}
		});
		yl=(TextView)findViewById(R.id.yl);
		yl.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="游乐场"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // 创建Intent对象
				intent.putExtra("scategory", scategory);
				startActivity(intent); // 启动Activity
				finish();
				
			}
		});
		js=(TextView)findViewById(R.id.js);
		js.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="健身"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // 创建Intent对象
				intent.putExtra("scategory", scategory);
				startActivity(intent); // 启动Activity
				finish();
				
			}
		});
		jd=(TextView)findViewById(R.id.jd);
		jd.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="景点"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // 创建Intent对象
				intent.putExtra("scategory", scategory);
				startActivity(intent); // 启动Activity
				finish();
				
			}
		});
		sc=(TextView)findViewById(R.id.sc);
		sc.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="商场"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // 创建Intent对象
				intent.putExtra("scategory", scategory);
				startActivity(intent); // 启动Activity
				finish();
				
			}
		});
		hs=(TextView)findViewById(R.id.hs);
		hs.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				scategory="会所"; 
				Intent intent = new Intent(SearchsyActivity.this,
						ProductListActivity.class); // 创建Intent对象
				intent.putExtra("scategory", scategory);
				startActivity(intent); // 启动Activity
				finish();
				
			}
		});
		
	}
	// 返回键设置
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if ((System.currentTimeMillis() - mExitTime) > 2000)
			{
				Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
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
