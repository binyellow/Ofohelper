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
	 * AsyncTaskʵ���첽�������� IPutstream��ȡjson���� NewsBean����洢
	 * 
	 */
	private ListView listView;
	private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);	//���ر���
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		new NewAsyncTask().execute(URL);	//ͨ���첽����һ����ַ Ȼ�����json�����ȡ��Դ
	}

	class NewAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {
		//����ҳ���json����   ���Զ�����onPostExecute
		protected List<NewsBean> doInBackground(String... params) {
			return GetJsonData(params[0]);	//params[0]����ַ GetJsonData�����ǻ�ȡjson�����
		}

		protected void onPostExecute(List<NewsBean> result) {			
			super.onPostExecute(result);
			MyAdapter myAdapter = new MyAdapter(result, News_MainActivity.this);
			listView.setAdapter(myAdapter);
		}
		
		private List<NewsBean> GetJsonData(String url) {
			List<NewsBean> beans = new ArrayList<NewsBean>();	//����ҳ��ȡNewsBean��List����
			//jeson����Ĺ��캯����Ҫ����jeson��ʽ���ַ���
			//��url��������ת������������������ȡ
			try {
				String jsonString = readString(new java.net.URL(url).openConnection().getInputStream());
				Log.i("arvin", jsonString);
				try {
					JSONObject jsonObject = new JSONObject(jsonString);	//����json���� ����string���͵Ĵ���ҳ��ȡ������
					JSONArray jsonArray = jsonObject.getJSONArray("data");	//����ȡ��json������json����     ����ע����2�� �����ǵ��²��������ݵ�ԭ�� ��һ��������
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
				//�ֽ���ת��Ϊ�ַ���
				isr = new InputStreamReader(is,"utf-8");		
				//��Buffer����ʽ��ȡ
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
