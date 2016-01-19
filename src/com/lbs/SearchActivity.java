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
	LocationListener mLocationListener = null;//createʱע���listener��Destroyʱ��ҪRemove
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
//		option.setOpenGps(true); // ��gps
//		option.setCoorType("bd09ll"); // ������������Ϊbd09ll
//		// ����Ϊall��ʾ���ص�ַ��Ϣ������ֵ�������ص�ַ��Ϣ
//	    option.setAddrType("all");
//
//	    // �������ö�λģʽ��С��1����һ�ζ�λ;���ڵ���1����ʱ��λ
////	    option.setScanSpan(800);
//	    option.setScanSpan(5000);
////		option.setPriority(LocationClientOption.NetWorkFirst); // ������������
//		  option.setPriority(LocationClientOption.GpsFirst);
//		// true��ʾ���û��棬false��ʾ���û���
//		  option.disableCache(true);
//		mLocationClient.setLocOption(option);
//		mLocationClient.registerLocationListener(new BDLocationListener() {
//			@Override
//			public void onReceiveLocation(BDLocation location) {
//				if (location == null)
//					return;
//				latitude = location.getLatitude();     //������Ϣ
//				longitude = location.getLongitude();   //ά����Ϣ
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
//				params.put("UID", UID);//���û���UID����γ�ȣ����͸���������
//				if(l1.equals("4.9E-324")||l2.equals("4.9E-324"))
//				{
//					System.out.println("haha");
//					return;
//				}
//				String result = HttpUtils.sendHttpClientPost(path, params,
//						"utf-8");
//				System.out.println("result-->>" + result);//�������û�����Ȥ��object��OID
//				
//				List<Object> params1 = new ArrayList<Object>();
//				params1.add(UID);
//				params1.add(result);
//				//��װһ��notification
//				addNotificaction(params1);
//				
//				String result1 = HttpUtils.sendHttpClientPost(path1, params,
//						"utf-8");
//				System.out.println("result1-->>" + result1);//�����������û�����Ȥ��object��OID
//				
//				if(!result1.equals("null")&&result1!=result)
//				{
//				List<Object> params2 = new ArrayList<Object>();
//				params2.add(UID);
//				params2.add(result1);
//				//��װһ��notification
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

// ע�ᶨλ�¼�
mLocationListener = new LocationListener(){

	@Override
	public void onLocationChanged(Location location) {
		if(location != null){
			String strLog = String.format("����ǰ��λ��:\r\n" +
					"����:%f\r\n" +
					"γ��:%f",
					location.getLongitude(), location.getLatitude());

			latitude = location.getLatitude();     //������Ϣ
			longitude = location.getLongitude();   //ά����Ϣ
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
			params.put("UID", UID);//���û���UID����γ�ȣ����͸���������
			if(l1.equals("4.9E-324")||l2.equals("4.9E-324"))
			{
				System.out.println("haha");
				return;
			}
			String result = HttpUtils.sendHttpClientPost(path, params,
					"utf-8");
			System.out.println("result-->>" + result);//�������û�����Ȥ��object��OID
			String update = HttpUtils.sendHttpClientPost(path2, params,
					"utf-8");
			System.out.println("update-->>" + update);
			List<Object> params1 = new ArrayList<Object>();
			params1.add(UID);
			params1.add(result);
			//��װһ��notification
			addNotificaction(params1);
			
			String result1 = HttpUtils.sendHttpClientPost(path1, params,
					"utf-8");
			System.out.println("result1-->>" + result1);//�����������û�����Ȥ��object��OID
			
			if(!result1.equals("null")&&result1!=result)
			{
			List<Object> params2 = new ArrayList<Object>();
			params2.add(UID);
			params2.add(result1);
			//��װһ��notification
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
				startActivity(intent); // ��������Activity
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
				startActivity(intent); // ��������Activity
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
						ProductListActivity.class); // ����Intent����
				startActivity(intent); // ����Activity
				finish();
				
			}
		});
		ImageButton ibExit = (ImageButton) findViewById(R.id.ibExit1);
		ibExit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				android.os.Process.killProcess(android.os.Process.myPid()); // �������̣��˳�����
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
		// �Ƴ�listener
		app.manager.getLocationManager().removeUpdates(mLocationListener);
		app.manager.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
		MyApp app = (MyApp)this.getApplication();
		// ע��Listener
        app.manager.getLocationManager().requestLocationUpdates(mLocationListener);
		app.manager.start();
		
	}
	
	private void addNotificaction(List<Object> params)
	{
		NotificationManager manager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
				// ����һ��Notification
				Notification notification = new Notification();
				// ������ʾ���ֻ����ϱߵ�״̬����ͼ��
				notification.icon = R.drawable.excel;
				// ����ǰ��notification���ŵ�״̬���ϵ�ʱ����ʾ����
				
				notification.flags = Notification.FLAG_AUTO_CANCEL;// ���֪ͨ������
				notification.tickerText = (String) params.get(1);
				System.out.println("OID"+params.get(1));
				System.out.println("UID-->>"+params.get(0));
				
				String path = CommonUrl.JSON_URL;
				Map<String, String> params1 = new HashMap<String, String>();
				params1.put("OID",(String) params.get(1));
				params1.put("UID",(String) params.get(0));
				String result = HttpUtils.sendHttpClientPost(path, params1,
						"utf-8");
				System.out.println("result-->>" + result);//�������Ƽ���object��OID
				Product product = JsonTools.getProduct("object", result);
				Log.i(TAG, product.toString());
				String proname=product.getProname();
				String bcategory=product.getBcategory();
				String scategory=product.getScategory();
				String latitude=String.valueOf(product.getLatitude());
				String longitude=String.valueOf(product.getLongitude());
				String proimage=product.getProimage();
				// ���������ʾ
				//notification.defaults=Notification.DEFAULT_SOUND;
				// audioStreamType��ֵ����AudioManager�е�ֵ�������������ģʽ
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
				// ���״̬����ͼ����ֵ���ʾ��Ϣ����
				myApp = (MyApp) getApplication();
				notification.setLatestEventInfo(this,  proname, myApp.getusername()+"�������", pendingIntent);
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
				// ����û���������ݲ�������֤
				EditText epName = (EditText) findViewById(R.id.epName); // ��ò�Ʒ����EditText����
		

				String pname = epName.getEditableText().toString().trim(); // ��ò�Ʒ����
				
				System.out.println("pname-->>" + pname);
				
				Intent intent = new Intent(SearchActivity.this,
						ProductListActivity.class);
				intent.putExtra("proname", pname);
				
				System.out.println("UID-->?" + UID);
				startActivity(intent); // ��������Activity
				finish();
				Looper.loop();
			}
		}.start();
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
