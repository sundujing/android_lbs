package com.lbs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



public class HttpUtils
{
	public HttpUtils()
	{
		
	}

	public static String sendHttpClientPost(String path,
			Map<String, String> map, String encode)
	{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty())
		{
			for (Map.Entry<String, String> entry : map.entrySet())
			{
				 list.add(new
				 BasicNameValuePair(entry.getKey(),entry.getValue()));

			}
		}
		try
		{
			// ʵ�ֽ�����Ĳ�����װ�����У���������
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,encode);
			// ʹ��post��ʽ�ύ����
			HttpPost httpPost = new HttpPost(path);
			httpPost.setEntity(entity);
			// ָ��post����
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{
				return changeInputStream(httpResponse.getEntity().getContent(),
						encode);
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		// ʵ�ֽ�����Ĳ�����װ�����У���������
		return "";
	}

	/**
	 * ��һ��������ת����ָ��������ַ���
	 * 
	 * @param inputStream
	 * @param encode
	 * @return
	 */
	public static String changeInputStream(InputStream inputStream,
			String encode)
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null)
		{
			try
			{
				while ((len = inputStream.read(data)) != -1)
				{
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

}
