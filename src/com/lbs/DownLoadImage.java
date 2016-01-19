package com.lbs;

import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class DownLoadImage
{
	private String image_path;

	public DownLoadImage(String image_path)
	{
		this.image_path = image_path;
	}

	public void loadImage(final ImageCallBack callBack)
	{
		final Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				callBack.getDrawable((Drawable) msg.obj);
			}
		};
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					Drawable drawable = Drawable.createFromStream(new URL(
							image_path).openStream(), "");
					Message message = Message.obtain();
					message.obj = drawable;
					handler.sendMessage(message);
				} catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		}).start();
	}

	// 接口的回调方式
	public interface ImageCallBack
	{
		public void getDrawable(Drawable drawable);
	}
}
