package com.lbs;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.RouteOverlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class WalkActivity extends MapActivity {
    /** Called when the activity is first created. */
    //添加百度地图的相关控件
	private MapView mapView;
	private MyApp myApp;
	public	Double latitude, longitude;
	private BMapManager bMapManager;//加载地图引擎
	//百度地图的key
	private String keyString="24F044B357DF31FFC326DA29DD00A9FEA3D590F6";
	//在百度地图上添加一些控件，比如是放大或者缩小的控件
	private MapController mapController;
	private MKSearch mkSearch;
	private MKPlanNode start,end;//定义行走的起始点和终点
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk);
        mapView=(MapView)this.findViewById(R.id.bmapView);
        bMapManager =new BMapManager(WalkActivity.this);
        //必须要加载key
        bMapManager.init(keyString, new MKGeneralListener()
		{
			
			@Override
			public void onGetPermissionState(int arg0)
			{
				// TODO Auto-generated method stub
				if(arg0==300){
					Toast.makeText(WalkActivity.this, "输入的key有错，请核实", 1).show();
				}
			}
			
			@Override
			public void onGetNetworkState(int arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
       this.initMapActivity(bMapManager);
       mapView.setBuiltInZoomControls(true);//表示可以被设置缩放功能
       mapController = mapView.getController();
       //需要定义经纬度
       myApp = (MyApp) getApplication();
       latitude=myApp.getlatitude();
       longitude=myApp.getlongitude();
       GeoPoint geoPoint = new GeoPoint((int)(latitude*1E6), (int)(longitude*1E6));
       mapController.setCenter(geoPoint);//设置一个中心点
       mapController.setZoom(12);//设置缩放级别
       mkSearch = new MKSearch();
       mkSearch.init(bMapManager, new MySearchListener());
       start = new MKPlanNode();
       start.pt = new GeoPoint((int)(latitude*1E6), (int)(longitude*1E6));
       end = new MKPlanNode();
       Intent intent = getIntent();
     String  latitude1 = intent.getStringExtra("latitude1");
		String longitude1 = intent.getStringExtra("longitude1");
		double lat=Double.parseDouble(latitude1);
		double lon=Double.parseDouble(longitude1);
       end.pt = new GeoPoint((int)(lat*1E6),(int)(lon*1E6));
       mkSearch.walkingSearch(null, start, null, end);
     
       
    }
	public class MySearchListener implements MKSearchListener
	{

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1)
		{
			// TODO Auto-generated method stub
			if(arg0==null){return;}
			RouteOverlay routeOverlay = new RouteOverlay(WalkActivity.this, mapView);
			routeOverlay.setData(arg0.getPlan(0).getRoute(0));
			mapView.getOverlays().clear();
			mapView.getOverlays().add(routeOverlay);
			mapView.invalidate();
			mapView.getController().animateTo(arg0.getStart().pt);
		}
		
	}
@Override
protected void onDestroy()
{
	// TODO Auto-generated method stub
	super.onDestroy();
	if(bMapManager!=null)
	{
		bMapManager.destroy();
		bMapManager=null;
	}
}
@Override
protected void onResume()
{
	// TODO Auto-generated method stub
	super.onResume();
	if(bMapManager!=null)
	{
		bMapManager.start();
	}
}
@Override
protected void onPause()
{
	// TODO Auto-generated method stub
	super.onPause();
	if(bMapManager!=null)
	{
		bMapManager.stop();
	}
}
	@Override
	protected boolean isRouteDisplayed()
	{
		// TODO Auto-generated method stub
		return false;
		
	}
}