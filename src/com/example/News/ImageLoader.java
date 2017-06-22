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
		int MaxMemery = (int) Runtime.getRuntime().maxMemory();	//先获得最大内存
		int cacheSize = MaxMemery/4;
		mCache = new LruCache<String, Bitmap>(cacheSize){
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();		//每次把图片加入缓存时会调用 用来返回每张图片的大小
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
	//通过handler更新ui
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (mimageView.getTag().equals(murl)) {		//只有图片和url匹配时才设置图片 使他一一对应
				mimageView.setImageBitmap((Bitmap) msg.obj);
			}
		};
	};
	public void showImageByAsyncTask(ImageView imageView, String url) {
		Bitmap bitmap = getBitmapFromCache(url);		// 从缓存中取出对应的图片
		if (bitmap == null) {		// 如果缓存中没有，那么必须下载
//			imageView.setImageResource(R.drawable.ic_launcher);		// 为空的时候先设置为自带图标
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
//				Bitmap bitmap = GetBitmapFromUrl(url);	//通过下面的方法获取Bitmap对象 然后交给handler传给主线程更新UI
//				Message message = new Message().obtain();//获得massage对象
//				message.obj = bitmap;
//				handler.sendMessage(message);
//			}
//		}.start();
//	}
	
	public Bitmap GetBitmapFromUrl(String urlString){
		InputStream is = null;
		try {
			URL url = new URL(urlString);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();	//获得URL之后获得连接
			is = new BufferedInputStream(httpURLConnection.getInputStream());
			Bitmap bitmap = BitmapFactory.decodeStream(is);		//将输入流转换成Bitmap对象
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
			Bitmap bitmap = GetBitmapFromUrl(url);// 从网络获取图片 前面bitmap一直没内容 现在才从网上获取资源
			if (bitmap != null) {
				AddBitmapToCache(url, bitmap);	// 将图片加入缓存
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
//			if (mimageView.getTag().equals(murl)) {	//前面在Myadapter里面已经将图片对应url，如果对应才设置图片
//				mimageView.setImageBitmap(bitmap);
//			}
			 if(mimageView.getTag()==null){//初始化时并未设置Tag

			     mimageView.setTag(murl);

			}else if(mimageView.getTag().equals(murl)){//已经设置过Tag

			    mimageView.setImageBitmap(bitmap);

			}
		}

	}
}
