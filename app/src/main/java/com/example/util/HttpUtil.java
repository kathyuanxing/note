package com.example.util;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;


public class HttpUtil {
	public static HttpClient httpClient=new DefaultHttpClient();
	public final static String BaseUrl="http://172.16.134.5:8080/HttpService/";
	public static String getRequest(URI uri)throws Exception{
		HttpGet get=new HttpGet(uri);
		HttpResponse httpResponse=httpClient.execute(get);
		if(httpResponse.getStatusLine().getStatusCode()==200){
			String result =EntityUtils.toString(httpResponse.getEntity());
			return result;
		}else{
		Log.d("服务器响应代码", new Integer(httpResponse.getStatusLine().getStatusCode()).toString());
		return null;
		}
	}

	public static String postRequest(String url,Map<String,String> rawParams)throws Exception{
		HttpPost httpPost=new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		 for (String key : rawParams.keySet()) {
	            // 封装请求参数
	            params.add(new BasicNameValuePair(key, rawParams.get(key)));
	        }
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 获取服务器响应字符串
        	HttpEntity httpMsg=httpResponse.getEntity();
            String result = EntityUtils.toString(httpMsg);
            Log.d("result取值",result);
            return result;
        }
        return null;
	}
}