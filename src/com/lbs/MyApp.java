package com.lbs;

import android.app.Application;
import android.widget.Toast;


import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
//import com.baidu.mapapi.map.MKEvent;


public class MyApp extends Application
{
	private static MyApp mInstance = null;
	private String UID;
	private String username;
	private Double latitude;
	private Double longitude;
	public  String key="24F044B357DF31FFC326DA29DD00A9FEA3D590F6";
	BMapManager manager;
	
	public String getUID()
	{
		return UID;
	}
	public String getusername()
	{
		return username;
	}	
	public Double getlatitude()
	{
		return latitude;
	}	
	public Double getlongitude()
	{
		return longitude;
	}
	
	public void setUID(String UID)
	{
		this.UID = UID;
	}
	public void setusername(String username)
	{
		this.username=username;
	}
	public void setlatitude(Double latitude)
	{
		this.latitude=latitude;
	}
	public void setlongitude(Double longitude)
	{
		this.longitude=longitude;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		setUID("12345");
		setusername("sun");
	//	initBMapManager(this);
		manager= new BMapManager(this);
		manager.init(this.key, new MyGeneralListener());
		mInstance=this;
		setlatitude(32.11451);
		setlongitude(118.937391);
	}
//	public void initBMapManager(Context context)
//	{
//	if(manager==null)
//	{
//		manager = new BMapManager(context);
//	}
//	manager.init(key, null);

    	
//	}
	public static MyApp getInstance() {
		return mInstance;
	}
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener
    {
        
        @Override
        public void onGetNetworkState(int iError) 
        {
            
                Toast.makeText(MyApp.getInstance().getApplicationContext(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
          
         }

		@Override
		public void onGetPermissionState(int arg0)
		{
			
			if(arg0!=300)
			{
				Toast.makeText(MyApp.getInstance().getApplicationContext(), "输入的key有错，请核实", 1).show();
			}
		}
    }
    
  
	@Override
	//建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (manager != null) {
			manager.destroy();
			manager = null;
		}
		super.onTerminate();
	}
}
