package com.lbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.LocationListener;
import com.lbs.domain.Product;

public class SearchActivity extends Activity
{

	private String UID;
	private MyApp myApp;
//	private LocationClient mLocationClient = null;
	LocationListener mLocationListener = null;//create时注册此listener，Destroy时需要Remove
	private Button mStartBtn = null;
    public	String l1, l2;
    public  String ll=null;
    public  String ls=null;
    public	Double latitude, longitude;
    private static final String TAG = "SearchActivity";
    private int i=1;
    private long mExitTime;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		myApp = (MyApp) getApplication();
		UID = myApp.getUID();
		latitude=myApp.getlatitude();
		longitude=myApp.getlongitude();
		System.out.println("UID SEARCH-->>" + UID);
		Button psearch = (Button) findViewById(R.id.psearch);
		
		

		psearch.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				search();
			}
		});
		
mStartBtn = (Button) findViewById(R.id.getLocate);
		
//		mLocationClient = new LocationClient(this);
//		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true); // 打开gps
//		option.setCoorType("bd09ll"); // 设置坐标类型为bd09ll
//		// 设置为all表示返回地址信息，其他值都不返回地址信息
//	    option.setAddrType("all");
//
//	    // 设置设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
////	    option.setScanSpan(800);
//	    option.setScanSpan(5000);
////		option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
//		  option.setPriority(LocationClientOption.GpsFirst);
//		// true表示禁用缓存，false表示启用缓存
//		  option.disableCache(true);
//		mLocationClient.setLocOption(option);
//		mLocationClient.registerLocationListener(new BDLocationListener() {
//			@Override
//			public void onReceiveLocation(BDLocation location) {
//				if (location == null)
//					return;
//				latitude = location.getLatitude();     //精度信息
//				longitude = location.getLongitude();   //维度信息
//				l1 = latitude.toString();   
//				l2 = longitude.toString();
//				Log.d("ll", l1);
//				Log.d("ls", l2);
//				if(l1.equals(ll)&&l2.equals(ls))
//				{
//					return;
//				}
//				ll=l1;
//				ls=l2;
//				Toast.makeText(SearchActivity.this, l1+l2, Toast.LENGTH_LONG).show();
//				
//				
//				String path = CommonUrl.PUSH_URL;
//				String path1 = CommonUrl.PUSH_URL1;
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("latitude", l1);
//				params.put("longitude", l2);
//				System.out.println("uid-->"+UID);
//				params.put("UID", UID);//将用户的UID，经纬度，发送给服务器端
//				if(l1.equals("4.9E-324")||l2.equals("4.9E-324"))
//				{
//					System.out.println("haha");
//					return;
//				}
//				String result = HttpUtils.sendHttpClientPost(path, params,
//						"utf-8");
//				System.out.println("result-->>" + result);//返回了用户感兴趣的object的OID
//				
//				List<Object> params1 = new ArrayList<Object>();
//				params1.add(UID);
//				params1.add(result);
//				//封装一个notification
//				addNotificaction(params1);
//				
//				String result1 = HttpUtils.sendHttpClientPost(path1, params,
//						"utf-8");
//				System.out.println("result1-->>" + result1);//返回了其他用户感兴趣的object的OID
//				
//				if(!result1.equals("null")&&result1!=result)
//				{
//				List<Object> params2 = new ArrayList<Object>();
//				params2.add(UID);
//				params2.add(result1);
//				//封装一个notification
//				addNotificaction(params2);
//				}
//				
//			}
//
//			
//			@Override
//			public void onReceivePoi(BDLocation arg0) {
//				// TODO Auto-generated method stub
//			}
//		});

MyApp app = (MyApp)this.getApplication();
if (app.manager == null) {
	app.manager = new BMapManager(getApplication());
	app.manager.init(app.key, new MyApp.MyGeneralListener());
}
app.manager.start();

// 注册定位事件
mLocationListener = new LocationListener(){

	@Override
	public void onLocationChanged(Location location) {
		if(location != null){
			String strLog = String.format("您当前的位置:\r\n" +
					"经度:%f\r\n" +
					"纬度:%f",
					location.getLongitude(), location.getLatitude());

			latitude = location.getLatitude();     //精度信息
			longitude = location.getLongitude();   //维度信息
			l1 = latitude.toString();   
			l2 = longitude.toString();
			Log.d("ll", l1);
			Log.d("ls", l2);
			if(l1.equals(ll)&&l2.equals(ls))
			{
				return;
			}
			ll=l1;
			ls=l2;
			
	//		Toast.makeText(SearchActivity.this, l1+l2, Toast.LENGTH_LONG).show();
			Toast.makeText(SearchActivity.this, strLog, Toast.LENGTH_LONG).show();
			myApp = (MyApp) getApplication();
			myApp.setlatitude(latitude);
			myApp.setlongitude(longitude);
//			TextView mainText = (TextView)findViewById(R.id.textview);
//	        mainText.setText(strLog);
			
			
			
			String path = CommonUrl.PUSH_URL;
			String path1 = CommonUrl.PUSH_URL1;
			String path2 =CommonUrl.UPDATE;
			Map<String, String> params = new HashMap<String, String>();
			params.put("latitude", l1);
			params.put("longitude", l2);
			System.out.println("uid-->"+UID);
			params.put("UID", UID);//将用户的UID，经纬度，发送给服务器端
			if(l1.equals("4.9E-324")||l2.equals("4.9E-324"))
			{
				System.out.println("haha");
				return;
			}
			String result = HttpUtils.sendHttpClientPost(path, params,
					"utf-8");
			System.out.println("result-->>" + result);//返回了用户感兴趣的object的OID
			String update = HttpUtils.sendHttpClientPost(path2, params,
					"utf-8");
			System.out.println("update-->>" + update);
			List<Object> params1 = new ArrayList<Object>();
			params1.add(UID);
			params1.add(result);
			//封装一个notification
			addNotificaction(params1);
			
			String result1 = HttpUtils.sendHttpClientPost(path1, params,
					"utf-8");
			System.out.println("result1-->>" + result1);//返回了其他用户感兴趣的object的OID
			
			if(!result1.equals("null")&&result1!=result)
			{
			List<Object> params2 = new ArrayList<Object>();
			params2.add(UID);
			params2.add(result1);
			//封装一个notification
			addNotificaction(params2);
			}
		}
	}
};

		mStartBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		ImageButton cy=(ImageButton)findViewById(R.id.cy);
		cy.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(SearchActivity.this,
						SearchscActivity.class);
				startActivity(intent); // 启动功能Activity
				finish();
				
			}
		});
		ImageButton yl=(ImageButton)findViewById(R.id.yl);
		yl.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(SearchActivity.this,
						SearchsyActivity.class);
				startActivity(intent); // 启动功能Activity
				finish();
			}
		});
		ImageButton all=(ImageButton)findViewById(R.id.all);
		all.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(SearchActivity.this,
						ProductListActivity.class); // 创建Intent对象
				startActivity(intent); // 启动Activity
				finish();
				
			}
		});
		ImageButton ibExit = (ImageButton) findViewById(R.id.ibExit1);
		ibExit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				android.os.Process.killProcess(android.os.Process.myPid()); // 结束进程，退出程序
			}
		});
	}


//	@Override
//	public void onDestroy() {
//		if (mLocationClient != null && mLocationClient.isStarted()) {
//			mLocationClient.stop();
//			mLocationClient = null;
//
//			// unbindService(serviceconnection);
//		}
//		super.onDestroy();
//	}
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		MyApp app = (MyApp)this.getApplication();
		if(app.manager!=null)
		{
			app.manager.destroy();
			app.manager=null;
		}
	}
	@Override
	protected void onPause() {
		MyApp app = (MyApp)this.getApplication();
		// 移除listener
		app.manager.getLocationManager().removeUpdates(mLocationListener);
		app.manager.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
		MyApp app = (MyApp)this.getApplication();
		// 注册Listener
        app.manager.getLocationManager().requestLocationUpdates(mLocationListener);
		app.manager.start();
		
	}
	
	private void addNotificaction(List<Object> params)
	{
		NotificationManager manager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
				// 创建一个Notification
				Notification notification = new Notification();
				// 设置显示在手机最上边的状态栏的图标
				notification.icon = R.drawable.excel;
				// 当当前的notification被放到状态栏上的时候，提示内容
				
				notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击通知后消除
				notification.tickerText = (String) params.get(1);
				System.out.println("OID"+params.get(1));
				System.out.println("UID-->>"+params.get(0));
				
				String path = CommonUrl.JSON_URL;
				Map<String, String> params1 = new HashMap<String, String>();
				params1.put("OID",(String) params.get(1));
				params1.put("UID",(String) params.get(0));
				String result = HttpUtils.sendHttpClientPost(path, params1,
						"utf-8");
				System.out.println("result-->>" + result);//返回了推荐的object的OID
				Product product = JsonTools.getProduct("object", result);
				Log.i(TAG, product.toString());
				String proname=product.getProname();
				String bcategory=product.getBcategory();
				String scategory=product.getScategory();
				String latitude=String.valueOf(product.getLatitude());
				String longitude=String.valueOf(product.getLongitude());
				String proimage=product.getProimage();
				// 添加声音提示
				//notification.defaults=Notification.DEFAULT_SOUND;
				// audioStreamType的值必须AudioManager中的值，代表着响铃的模式
				//notification.audioStreamType= android.media.AudioManager.ADJUST_LOWER;
				Intent intent = new Intent(this, ItemActivity.class);
				intent.putExtra("OID",  (String) params.get(1));
				intent.putExtra("UID", (String)params.get(0));
				intent.putExtra("proname", proname);
				intent.putExtra("bcategory", bcategory);
				intent.putExtra("scategory", scategory);
				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				intent.putExtra("proimage", proimage);
				PendingIntent pendingIntent = PendingIntent.getActivity(this, i, intent, PendingIntent.FLAG_ONE_SHOT);
				// 点击状态栏的图标出现的提示信息设置
				myApp = (MyApp) getApplication();
				notification.setLatestEventInfo(this,  proname, myApp.getusername()+"点击看看", pendingIntent);
				manager.notify(i, notification);
				i++;
			//	startActivity(intent);
	}

	
	
	public void search()
	{
		new Thread()
		{
			public void run()
			{
				Looper.prepare();
				// 获得用户输入的数据并进行验证
				EditText epName = (EditText) findViewById(R.id.epName); // 获得产品名称EditText对象
		

				String pname = epName.getEditableText().toString().trim(); // 获得产品名称
				
				System.out.println("pname-->>" + pname);
				
				Intent intent = new Intent(SearchActivity.this,
						ProductListActivity.class);
				intent.putExtra("proname", pname);
				
				System.out.println("UID-->?" + UID);
				startActivity(intent); // 启动功能Activity
				finish();
				Looper.loop();
			}
		}.start();
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
