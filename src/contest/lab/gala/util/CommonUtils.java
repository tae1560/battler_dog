package contest.lab.gala.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class CommonUtils {
	
	//////// UI builder module
	public static void arrangeView(View view, float _x, float _y, float width, float height, float standWidth, float standHeight) {
        
        ViewGroup parent = (ViewGroup) view.getParent();
        //float width, float height,
        float x = _x;
        float y = _y;
        
        parent.removeView(view);
        
        // set base layout
        LinearLayout baseLayout = new LinearLayout(parent.getContext());
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams base_layout_params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
        baseLayout.setLayoutParams(base_layout_params);
        
        // add top layout
        LinearLayout topLayout = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams top_layout_params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, y);
        topLayout.setLayoutParams(top_layout_params);
        baseLayout.addView(topLayout);
        
        // add mid layout
        LinearLayout midLayout = new LinearLayout(parent.getContext());
        midLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams mid_layout_params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, height);
        midLayout.setLayoutParams(mid_layout_params);
        baseLayout.addView(midLayout);
        
        // add bottom layout
        LinearLayout botLayout = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams bot_layout_params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, standHeight - y - height);
        botLayout.setLayoutParams(bot_layout_params);
        baseLayout.addView(botLayout);
        
        // add left layout
        LinearLayout leftLayout = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams left_layout_params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, x);
        leftLayout.setLayoutParams(left_layout_params);
        midLayout.addView(leftLayout);
        
        // add center layout
        LinearLayout centerLayout = new LinearLayout(parent.getContext());
        centerLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams center_layout_params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, width);
        centerLayout.setLayoutParams(center_layout_params);
        midLayout.addView(centerLayout);
        
        // add right layout
        LinearLayout rightLayout = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams right_layout_params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, standWidth - x - width);
        rightLayout.setLayoutParams(right_layout_params);
        midLayout.addView(rightLayout);
        
        centerLayout.addView(view);
        
        parent.addView(baseLayout);
	}
	
	//////// ??? ?µì? ëª¨ë?
	private static final int timeoutConnection = 3000;
	private static final int timeoutSocket = 3000;
	
	public static String requestWithPost(String url) {
		return requestWithPost(url, null);
	}
		
	public static String requestWithPost(String postURL, Map<String, String> m_params) {
		try
		{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        
	        if (m_params != null) {
	        	for (String key : m_params.keySet()) {
		        	params.add(new BasicNameValuePair(key, m_params.get(key)));
				}
	        }
			
		        HttpClient client = new DefaultHttpClient(httpParameters);  
		        HttpPost post = new HttpPost(postURL); 
		        
		        
		        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
		        post.setEntity(ent);
		        HttpResponse responsePOST = client.execute(post);  
		        HttpEntity resEntity = responsePOST.getEntity();
		        
		        if (resEntity != null)
		        {
		        	String result = EntityUtils.toString(resEntity);
		        	Log.d("NETWORK_RESULT", result);
		        	return result;
		        }
		}
		catch (Exception e)
		{
		        e.printStackTrace();
		}
		
		return null;
	}
	
	public static String requestWithGet(String getURL) {
		return requestWithGet(getURL, null);
	}
	public static String requestWithGet(String getURL, Map<String, String> m_params) {
		try
		{
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			
			int index = 0;
	        if (m_params != null) {
	        	for (String key : m_params.keySet()) {
		        	if (index++ == 0)
		        		getURL += "?";
		        	else
		        		getURL += "&";
		        	
		        	getURL += key + "=" +  m_params.get(key);
				}
	        }

	        Log.d("NETWORK_RESULT", getURL);
	        	
		        HttpClient client = new DefaultHttpClient(httpParameters);  
		        HttpGet get = new HttpGet(getURL);
		        
		        	        
		        
		        HttpResponse responseGet = client.execute(get);  
		        HttpEntity resEntityGet = responseGet.getEntity();
		        
		        if (resEntityGet != null)
		        {  
		                // ê²°ê³¼ë¥?ì²?¦¬?©ë???
		        	String result = EntityUtils.toString(resEntityGet);
		        	Log.d("NETWORK_RESULT", result);
		            return result;
		        }
		}
		catch (Exception e)
		{
		        e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	//////// network state check
	public static boolean isNetworkEnable(Context context) {
		return isConnected3G(context) || isConnectedWiFi(context);
	}
	
	/**
	* Wi-Fiê°??°ê²°??? ???ê°?	* @return true=?°ê²°??	*/
	public static boolean isConnectedWiFi(Context context)
	{
	ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	return manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
	}
	/**
	* 3Gê°??°ê²°??? ???ê°?	* @return true=?°ê²°??	*/
	public static boolean isConnected3G(Context context)
	{
	boolean kResult = false;
	try
	{
	ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	kResult = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	return kResult;
	}
	
	
	public static void copy_database(Context context, String strURL, String database_name) {
		File f = new File("/data/data/"+context.getPackageName()+"/databases/" + database_name);
 
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
 
		try {
			InputStream is;
			URL url = new URL(strURL);
			is = url.openStream();
			BufferedInputStream bis = new BufferedInputStream(is);
 
			// ë§????????????ë©?ì§??ê³??¤ì? ???
			if (f.exists()) {
				f.delete();
				f.createNewFile();
			}
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
 
			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, read);
			}
			bos.flush();
 
			fos.close();
			bos.close();
			is.close();
			bis.close();
 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String get_uuid(Activity act) {
		String tmDevice=null, tmSerial=null, androidId=null;
		int tmDevice_hash = 0, tmSerial_hash = 0, androidId_hash = 0;
		try {
			final TelephonyManager tm = (TelephonyManager) act.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
			tmDevice = "" + tm.getDeviceId();
			tmSerial = "" + tm.getSimSerialNumber();
			tmDevice_hash = tmDevice.hashCode();
			tmSerial_hash = tmSerial.hashCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			androidId = "" + android.provider.Settings.Secure.getString(act.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			androidId_hash = androidId.hashCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		UUID deviceUuid = new UUID(androidId_hash, ((long)tmDevice_hash << 32) | tmSerial_hash);
		
		return deviceUuid.toString();
	}
	
	public static void debug(String string) {
		Log.e("CommonUtils.debug", string);
	}
}