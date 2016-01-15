package com.example.mjpurin.xmllesson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpManager {

	public String reqText(String url){

		//HTTP通信するための仕組み
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse res;
		HttpEntity entity;
		String str = "";

		try {
			//HTTP通信する(リクエストを送信する）
			res = client.execute(get);

			//ステータスコードのチェック(200:OK)
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

				//ボディ部を抽出する。
				entity = res.getEntity();

				//受信データを文字列に変換する。
				str = EntityUtils.toString(entity,"UTF-8");
			}

		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}finally{
			client.getConnectionManager().shutdown();
		}
		return str;
	}

	public String reqTextPost(String url,HashMap<String,String> hm){
		String str="";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpEntity entity;

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> entry:hm.entrySet()){
			nameValuePair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePair,"UTF-8"));
			HttpResponse res = client.execute(post);

			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

				//ボディ部を抽出する。
				entity = res.getEntity();

				//受信データを文字列に変換する。
				str = EntityUtils.toString(entity);
			}
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return str;

	}



	public Bitmap reqImage(String url){

		//HTTP通信するための仕組み
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse res;
		HttpEntity entity;
		Bitmap bmp = null;

		try {
			//HTTP通信する(リクエストを送信する）
			res = client.execute(get);

			//ステータスコードのチェック(200:OK)
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

				//ボディ部を抽出する。
				entity = res.getEntity();

				//入力データを取得する
				InputStream is = entity.getContent();

				//画像データに変換する
				bmp = BitmapFactory.decodeStream(is);

				
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}finally{
			client.getConnectionManager().shutdown();
		}
		return bmp;
	}


}
