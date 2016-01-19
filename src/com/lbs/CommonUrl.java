package com.lbs;

public class CommonUrl
{
	public static final String URL="10.0.2.2";
	//public static final String URL="112.86.187.131";
	//public static final String URL="10.5.6.31";
	//public static final String URL="172.27.35.1";
	//访问服务器产品表的链接
		public static final String PRODUCT_URL="http://"+URL+":8080/lbs/servlet/JsonAction?action_flag=more";
		public static final String JSON_URL="http://"+URL+":8080/lbs/servlet/JsonAction?action_flag=single";	
		//产品图片的链接
		public static final String PRODUCT_IMG="http://"+URL+":8080/lbs/upload/";
		public static final String REG_URL="http://"+URL+":8080/lbs/servlet/RegisterAction";
		public static final String LOGIN_URL="http://"+URL+":8080/lbs/servlet/LoginAction?action_flag=login";
		public static final String LOGIN_URL1="http://"+URL+":8080/lbs/servlet/LoginAction?action_flag=getuid";
		public static final String UPDATE="http://"+URL+":8080/lbs/servlet/ProductAction?action_flag=update";
		public static final String VIEW_URL="http://"+URL+":8080/lbs/servlet/JsonAction?action_flag=view";
		public static final String PUSH_URL="http://"+URL+":8080/lbs/servlet/PushAction?action_flag=userinterest";
		public static final String PUSH_URL1="http://"+URL+":8080/lbs/servlet/PushAction?action_flag=otherinterest";
}
