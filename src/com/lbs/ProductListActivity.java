package com.lbs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductListActivity extends Activity
{
	private ListView listView;
	private ProgressDialog dialog;
	private MyAdapter adapter;
	private static final String TAG = "ProductListActivity";
	private MyApp myApp;
	 private long mExitTime;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listView = (ListView) this.findViewById(R.id.listView1);
		dialog = new ProgressDialog(this);
		adapter = new MyAdapter(this);// ����������
		dialog.setTitle("��ʾ");
		dialog.setMessage("�������أ����Ժ�");
		new MyTask().execute(CommonUrl.PRODUCT_URL);//ִ���첽����
		Button btnBack = (Button) findViewById(R.id.button1); // ��÷��ذ�ť����
		btnBack.setOnClickListener(new View.OnClickListener()
		{ // Ϊ���ذ�ť��Ӽ�����
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ProductListActivity.this,
						SearchActivity.class); // ����Intent����
				startActivity(intent); // ����Activity
				finish();
			}
		});
	}

/**
 * ʹ���첽����Ĺ���1.����һ����̳�AsyncTask��ע��������������
 * 2.��һ��������ʾҪִ�е�����ͨ���������·�����ڶ���������ʾ���ȵĿ̶ȣ�������������ʾ����ִ�еķ��ؽ��
 * @author Administrator
 *
 */
	public class MyTask extends
			AsyncTask<String, Void, List<Map<String, Object>>>
	{
//��ʾ����ִ��֮ǰ�Ĳ���
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			dialog.show();
		}
//��Ҫ����UI����
		@Override
		protected void onPostExecute(List<Map<String, Object>> result)
		{
			super.onPostExecute(result);
			
			Sort(result);// ����
			
			adapter.setData(result);
			listView.setAdapter(adapter);//ΪListView�ؼ���������
			adapter.notifyDataSetChanged();
			dialog.dismiss();
		}
//��Ҫ��ɺ�ʱ����
		@Override
		protected List<Map<String, Object>> doInBackground(String... params)
		{
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Intent intent = getIntent();
			String proname = intent.getStringExtra("proname");
			String bcategory = intent.getStringExtra("bcategory");
			String scategory = intent.getStringExtra("scategory");

			Map<String, String> params1 = new HashMap<String, String>();
			params1.put("proname", proname);
			params1.put("bcategory", bcategory);
			params1.put("scategory", scategory);
			String result = HttpUtils.sendHttpClientPost(params[0], params1,
					"utf-8");
			// ת���������
			System.out.println("result-->>" + result);
	
			
			myApp = (MyApp) getApplication();
			double la = myApp.getlatitude();
			double lo = myApp.getlongitude();
			
			
			// �������磬��ȡjson���ݲ��ҽ��н���
//			list = JsonTools.listKeyMaps("objects", result);
			list = JsonTools.listKeyMaps("objects", result, la, lo);
			Log.i(TAG, list.toString());
			return list;
		}

	}
	
	private List<Map<String, Object>> Sort(List<Map<String, Object>> result) 
	{
		System.out.println("Sort");
		// ��result��������
		if(!result.isEmpty())
		{
			Collections.sort(result, new Comparator<Map<String, Object>>()
			{
				@Override
				public int compare(Map<String, Object> object1,
						Map<String, Object> object2)
				{
					if((Double)object1.get("distance")>(Double)object2.get("distance"))
					{
						return 1;
					}
					return -1;
					
					
				}
			});
		}
		return result;
	}

	

	public class MyAdapter extends BaseAdapter
	{

		private Context context;//�����Ķ���
		private LayoutInflater layoutInflater;// ��̬����ӳ�� 
		private List<Map<String, Object>> list = null;

		public MyAdapter(Context context)
		{
			this.context = context;
			layoutInflater = LayoutInflater.from(context);
		}

		public void setData(List<Map<String, Object>> list)
		{
			this.list = list;
		}

		@Override
		public int getCount()
		{
			return list.size();// ListView����Ŀ��  
		}

		@Override
		public Object getItem(int position)
		{
			return list.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent)
		{
			View view = null;
			if (convertView == null)
			{
				view = layoutInflater.inflate(R.layout.item, null);
			} else
			{
				view = convertView;
			}
			int listsize=list.size();
			if (!list.isEmpty())
			{
			double[] d = new double[listsize];
			d[0]=0.0;
			String[] o=new String[listsize];
				for (int i=0;i<listsize;i++)
				{
			TextView distance =(TextView)view.findViewById(R.id.distance);
			String latitude = list.get(position).get("latitude")
					.toString();
			double lat=Double.parseDouble(latitude);
			String longitude = list.get(position).get("longitude")
					.toString();
			double lon=Double.parseDouble(longitude);
			myApp = (MyApp) getApplication();
			double la=myApp.getlatitude();
			double lo=myApp.getlongitude();
			double s=getdistance(la, lo, lat, lon);
			
			if(s > 1000)
			{
				distance.setText(s/1000 +" km");
			}
			else
			{
				distance.setText(s+" m");
			}
			
			System.out.println("s--->>"+s);
			 d[i]=s;
				}
			//	BubbleSort(d, listsize);
				for (int i = 0; i < listsize; i++)
		              for (int j = 1; j < listsize - i; j++)
		                     if (d[j - 1] > d[j])
		                     {
		                            double t;
		                		    t=d[j - 1];
		                		    d[j - 1]=d[j];
		                		    d[j]=t;
		                            Object objA = list.get(j-1);
		                            list.set(j-1,list.get(j));
		                            list.set(j,(Map<String, Object>) objA);
		                     }
			}
			
			TextView name = (TextView) view.findViewById(R.id.textView6);
			name.setText(list.get(position).get("proname").toString());
			name.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					myApp = (MyApp) getApplication();
					String	UID = myApp.getUID();
					System.out.println("UID LIST-->>" + UID);
					String OID = list.get(position).get("OID").toString();
					String proname = list.get(position).get("proname")
							.toString();
					String bcategory = list.get(position).get("bcategory")
							.toString();
					String scategory = list.get(position).get("scategory")
							.toString();
					String latitude = list.get(position).get("latitude")
							.toString();
					String longitude = list.get(position).get("longitude")
							.toString();

					System.out.println("OID-->>" + OID + "proname--->>"
							+ proname + "bcategory-->>" + bcategory
							+ "scategory-->>" + scategory + "latitude-->>"
							+ latitude + "longitude-->>" + longitude);
					System.out.println("viewstart1");
					String proimage = CommonUrl.PRODUCT_IMG
							+ list.get(position).get("proimage").toString();
					System.out.println("proimage-->>" + proimage);
					Intent intent = new Intent(ProductListActivity.this,
							ItemActivity.class);
					intent.putExtra("OID", OID);
					intent.putExtra("proname", proname);
					intent.putExtra("bcategory", bcategory);
					intent.putExtra("scategory", scategory);
					intent.putExtra("latitude", latitude);
					intent.putExtra("longitude", longitude);
					intent.putExtra("proimage", proimage);
					startActivity(intent);
				}
			});
			return view;
		}
		//ð������1
		public void BubbleSort(double a[], int n)
		{
		       int i, j;
		       for (i = 0; i < n; i++)
		              for (j = 1; j < n - i; j++)
		                     if (a[j - 1] > a[j])
		                     {
		                            Swap(a[j - 1], a[j]);
		                            Object objA = list.get(j-1);
		                            list.set(j-1,list.get(j));
		                            list.set(j,(Map<String, Object>) objA);
		                     }
		}
//		void swap (List list, int a, int b){
//			  Object objA = list.get(a);
//			  list.set(a, list.get(b));
//			  list.set(b, objA);
//			}
		private void Swap(double i, double j)
		{
			
			double t;
		    t=i;
		    i=j;
		    j=t;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return super.onCreateOptionsMenu(menu);
	}
	
	public double getdistance(double la,double lo,double lat,double lon)
	{
		
		double a=(la-lat)* Math.PI / 180.0;
		double b=(lo-lon)* Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	 Math.cos(la* Math.PI / 180.0)*Math.cos(lat* Math.PI / 180.0)*Math.pow(Math.sin(b/2),2)));
		s=s* 6378137;
	      s = Math.round(s * 10000) / 10000;
	      return s;
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