package com.example.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ofohelperdemo.R;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class News_MainActivity extends Activity {
	/**
	 * AsyncTask实现异步加载网络 IPutstream读取json对象、 NewsBean对象存储
	 * 
	 */
	private ListView listView;
	private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);	//隐藏标题
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		new NewAsyncTask().execute(URL);	//通过异步传入一个网址 然后解析json对象获取资源
	}

	class NewAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {
		//从网页获得json数据   并自动传入onPostExecute
		protected List<NewsBean> doInBackground(String... params) {
			return GetJsonData(params[0]);	//params[0]即网址 GetJsonData方法是获取json对象的
		}

		protected void onPostExecute(List<NewsBean> result) {			
			super.onPostExecute(result);
			MyAdapter myAdapter = new MyAdapter(result, News_MainActivity.this);
			listView.setAdapter(myAdapter);
		}
		
		private List<NewsBean> GetJsonData(String url) {
			List<NewsBean> beans = new ArrayList<NewsBean>();	//从网页获取NewsBean的List集合
			//jeson对象的构造函数需要传入jeson格式的字符串
			//将url连接网络转换成输入流对象来读取
			try {
				String jsonString = readString(new java.net.URL(url).openConnection().getInputStream());
				Log.i("arvin", jsonString);
				try {
					JSONObject jsonObject = new JSONObject(jsonString);	//创建json对象 传入string类型的从网页获取的数据
					JSONArray jsonArray = jsonObject.getJSONArray("data");	//将获取的json对象传入json数组     着重注意这2行 本行是导致不加载数据的原因 下一行是数量
					for (int i = 0; i < jsonArray.length(); i++) 
					{
						jsonObject = jsonArray.getJSONObject(i);
						NewsBean newsBean = new NewsBean();
						newsBean.setImageView(jsonObject.getString("picSmall"));
						newsBean.setTitle(jsonObject.getString("name"));
						newsBean.setContent(jsonObject.getString("description"));
						Log.i("arvin", newsBean.toString());
						beans.add(newsBean);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return beans;
		}
		
		private String readString(InputStream is) {
//			InputStreamReader isr = null;
//			try {
//				isr = new InputStreamReader(is,"UTF_8");
//			} catch (UnsupportedEncodingException e1) {
//				e1.printStackTrace();
//			}
//			String Line = "";
//			String Result = null;
//			BufferedReader br = new BufferedReader(isr);
//			try {
//				while ((Line = br.readLine())!=null) {
//					Result+=Line;
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return Result;
			InputStreamReader isr;
			String result = "";
			try {
				String line = "";
				//字节流转换为字符流
				isr = new InputStreamReader(is,"utf-8");		
				//以Buffer的形式读取
				BufferedReader br = new BufferedReader(isr);
				while((line = br.readLine()) != null)
				{
					result += line;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
		}
	}
}
