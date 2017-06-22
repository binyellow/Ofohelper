package com.example.News;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class ImageLoader {
	private ImageView mimageView;
	private String murl;
	private LruCache<String , Bitmap> mCache;
	
	public  ImageLoader(){
		int MaxMemery = (int) Runtime.getRuntime().maxMemory();	//�Ȼ������ڴ�
		int cacheSize = MaxMemery/4;
		mCache = new LruCache<String, Bitmap>(cacheSize){
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();		//ÿ�ΰ�ͼƬ���뻺��ʱ����� ��������ÿ��ͼƬ�Ĵ�С
			}
		};
	}
	
	public void AddBitmapToCache(String url,Bitmap bitmap){
		if (mCache.get(url)==null) {
			mCache.put(url, bitmap);
		}
	}
	
	public Bitmap getBitmapFromCache(String url){	
		return mCache.get(url);		
	}
	//ͨ��handler����ui
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (mimageView.getTag().equals(murl)) {		//ֻ��ͼƬ��urlƥ��ʱ������ͼƬ ʹ��һһ��Ӧ
				mimageView.setImageBitmap((Bitmap) msg.obj);
			}
		};
	};
	public void showImageByAsyncTask(ImageView imageView, String url) {
		Bitmap bitmap = getBitmapFromCache(url);		// �ӻ�����ȡ����Ӧ��ͼƬ
		if (bitmap == null) {		// ���������û�У���ô��������
//			imageView.setImageResource(R.drawable.ic_launcher);		// Ϊ�յ�ʱ��������Ϊ�Դ�ͼ��
			new NewsAsyncTask(imageView,murl).execute(url);
		} else {
			imageView.setImageBitmap(bitmap);
		}
//		new NewsAsyncTask(imageView,murl).execute(url);
	}
	
//	public void ShowImageByThread(ImageView imageView,final String url){
//		mimageView = imageView;
//		murl = url;
//		new Thread(){
//			public void run() {
//				super.run();
//				Bitmap bitmap = GetBitmapFromUrl(url);	//ͨ������ķ�����ȡBitmap���� Ȼ�󽻸�handler�������̸߳���UI
//				Message message = new Message().obtain();//���massage����
//				message.obj = bitmap;
//				handler.sendMessage(message);
//			}
//		}.start();
//	}
	
	public Bitmap GetBitmapFromUrl(String urlString){
		InputStream is = null;
		try {
			URL url = new URL(urlString);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();	//���URL֮��������
			is = new BufferedInputStream(httpURLConnection.getInputStream());
			Bitmap bitmap = BitmapFactory.decodeStream(is);		//��������ת����Bitmap����
			httpURLConnection.disconnect();
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {

		public NewsAsyncTask(ImageView imageView,String url) {
			mimageView = imageView;
			murl = url;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			String url = params[0];
			Bitmap bitmap = GetBitmapFromUrl(url);// �������ȡͼƬ ǰ��bitmapһֱû���� ���ڲŴ����ϻ�ȡ��Դ
			if (bitmap != null) {
				AddBitmapToCache(url, bitmap);	// ��ͼƬ���뻺��
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
//			ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
//			if (imageView != null && bitmap != null) {
//				imageView.setImageBitmap(bitmap);
//			}
//			mTask.remove(this);
//			if (mimageView.getTag().equals(murl)) {	//ǰ����Myadapter�����Ѿ���ͼƬ��Ӧurl�������Ӧ������ͼƬ
//				mimageView.setImageBitmap(bitmap);
//			}
			 if(mimageView.getTag()==null){//��ʼ��ʱ��δ����Tag

			     mimageView.setTag(murl);

			}else if(mimageView.getTag().equals(murl)){//�Ѿ����ù�Tag

			    mimageView.setImageBitmap(bitmap);

			}
		}

	}
}
