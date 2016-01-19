package com.lbs;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.lbs.DownLoadImage.ImageCallBack;

public class ItemActivity extends Activity
{
	private TextView name;
	private TextView bcategory;
	private TextView scategory;
	private TextView latitude;
	private TextView longitude;
	private String latitude1;
	private String longitude1;
	private MyApp myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items);
		name = (TextView) this.findViewById(R.id.textView1);
		bcategory = (TextView) this.findViewById(R.id.textView2);
		scategory = (TextView) this.findViewById(R.id.textView3);
		latitude = (TextView) this.findViewById(R.id.textView4);
		longitude = (TextView) this.findViewById(R.id.textView5);
		Intent intent = getIntent();
		String OID = intent.getStringExtra("OID");
		String proname = intent.getStringExtra("proname");
		String bcategory = intent.getStringExtra("bcategory");
		String scategory = intent.getStringExtra("scategory");
		 latitude1 = intent.getStringExtra("latitude");
		 longitude1 = intent.getStringExtra("longitude");
		String proimage = intent.getStringExtra("proimage");
//		String UID=intent.getStringExtra("UID");
		myApp=(MyApp)getApplication();
		String UID=myApp.getUID();
			System.out.println("UID ITEM-->>"+UID);
		name.setText(proname);
		this.bcategory.setText(bcategory);
		this.scategory.setText(scategory);
		this.latitude.setText(latitude1);
		this.longitude.setText(longitude1);

		// 加载图片
		final ImageView imageView = (ImageView) this
				.findViewById(R.id.imageView1);

		DownLoadImage downloadImage = new DownLoadImage(proimage);
		downloadImage.loadImage(new ImageCallBack()
		{

			@Override
			public void getDrawable(Drawable drawable)
			{
				imageView.setImageDrawable(drawable);

			}
		});
		String path=CommonUrl.VIEW_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("UID", UID);
		params.put("OID",OID);
		System.out.println("uid-->?"+UID);
		String result= HttpUtils.sendHttpClientPost(path, params, "utf-8");
		System.out.println("result--->>"+result);
		Button walk = (Button) findViewById(R.id.walk); // 获得返回按钮对象
		walk.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ItemActivity.this,
						WalkActivity.class); // 创建Intent对象
				intent.putExtra("latitude1", latitude1);
				intent.putExtra("longitude1", longitude1);
				startActivity(intent); // 启动Activity
				finish();
			//	walk();
			}
		});
	}
//	public void walk()
//	{
//		new Thread()
//		{
//			public void run()
//			{
//				Looper.prepare();
//				Intent intent = new Intent(ItemActivity.this,
//						WalkActivity.class); // 创建Intent对象
//				
//				startActivity(intent); // 启动Activity
//				finish();
//				Looper.loop();
//			}
//		}.start();
//	}
}
