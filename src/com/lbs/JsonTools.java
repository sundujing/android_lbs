package com.lbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lbs.domain.Product;

/**
 * 完成对json数据的解析
 * 
 * @author Administrator
 * 
 */
public class JsonTools
{
	public JsonTools()
	{

	}

	public static Product getProduct(String key, String jsonString)
	{
		Product product = new Product();
		try
		{
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject productObject = jsonObject.getJSONObject(key);
			product.setOID(productObject.getString("OID"));
			product.setProname(productObject.getString("proname"));
			product.setBcategory(productObject.getString("bcategory"));
			product.setScategory(productObject.getString("scategory"));
			product.setLatitude(productObject.getDouble("latitude"));
			product.setLongitude(productObject.getDouble("longitude"));
			product.setProimage(productObject.getString("proimage"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return product;
	}

	public static List<Product> getProducts(String key, String jsonString)
	{
		List<Product> list = new ArrayList<Product>();
		try
		{
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Product product = new Product();
				product.setOID(jsonObject2.getString("OID"));
				product.setProname(jsonObject2.getString("proname"));
				product.setBcategory(jsonObject2.getString("bcategory"));
				product.setScategory(jsonObject2.getString("scategory"));
				product.setLatitude(jsonObject2.getDouble("latitude"));
				product.setLongitude(jsonObject2.getDouble("longitude"));
				product.setProimage(jsonObject2.getString("proimage"));
				list.add(product);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getList(String key, String jsonString)
	{
		List<String> list = new ArrayList<String>();
		try
		{
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++)
			{
				String msg = jsonArray.getString(i);
				list.add(msg);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String, Object>> listKeyMaps(String key,
			String jsonString, double la, double lo)
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		double latitude = 0;// 修改
		double longitude = 0;// 修改
		
		try
		{
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				
				@SuppressWarnings("unchecked")
				Iterator<String> iterator = jsonObject2.keys();
				while (iterator.hasNext())
				{
					String json_key = iterator.next();
					Object json_value = jsonObject2.get(json_key);
					if (json_value == null)
					{
						json_value = "";
					}
					map.put(json_key, json_value);
					
					// 修改
					if(json_key.equals("latitude"))
					{
						latitude = (Double) json_value;
					}
					if(json_key.equals("longitude"))
					{
						longitude = (Double) json_value;
					}
					
				}
				
				double s = getdistance(la, lo, latitude, longitude);// 修改
				map.put("distance", s);// 修改
				
				list.add(map);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	// 计算距离
	public static double getdistance(double la, double lo, double lat,
			double lon)
	{
		System.out.println("la=" + la + "lo=" + lo + "lat=" + lat + "lon="
				+ lon);
		double a = (la - lat) * Math.PI / 180.0;
		double b = (lo - lon) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(la * Math.PI / 180.0)
				* Math.cos(lat * Math.PI / 180.0)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	
}
