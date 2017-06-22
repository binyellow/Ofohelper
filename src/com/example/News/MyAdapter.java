package com.example.News;

import java.util.List;
import java.util.zip.Inflater;

import com.example.ofohelperdemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	private List<NewsBean> mList;
	private LayoutInflater inflater;
	private ImageLoader mimageLoader;
	private int mStart,mEnd;
	public static String[] URLS;
	
	public MyAdapter(List<NewsBean> data,Context context){
		mList = data;
		inflater = LayoutInflater.from(context);
		mimageLoader = new ImageLoader();	//初始化ImageLoader，避免后面重复创建
		URLS = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			URLS[i] = data.get(i).imageViewURL;
		}
	}
	public int getCount() {
		
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {	//如果该view对象为空 则创建 否则 直接通过Gettag()方法获得
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_layout, null);	//将显示样式转换成View对象
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);	//初始化holder的组件 find样式中的id
			holder.Title = (TextView) convertView.findViewById(R.id.Item_tv1);
			holder.conten = (TextView) convertView.findViewById(R.id.Item_tv2);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		//设置控件的资源
		holder.imageView.setImageResource(R.drawable.ic_launcher);
		String url = mList.get(position).imageViewURL;
		
		holder.imageView.setTag(url);		//将图片和url绑定
		mimageLoader.showImageByAsyncTask(holder.imageView, url);	//设置图片
		holder.Title.setText(mList.get(position).Title);		//设置标题
		holder.conten.setText(mList.get(position).content);
		return convertView;
	}
	//通过该对象 避免重复创建View对象
	class ViewHolder{
		ImageView imageView;
		TextView Title,conten;
	}
//	public void onScrollStateChanged(AbsListView view, int scrollState) {
//		if (scrollState==SCROLL_STATE_IDLE) {	//表示此时滑动在停止状态
//			
//		}
//	}
//	@Override
//	public void onScroll(AbsListView view, int firstVisibleItem,
//			int visibleItemCount, int totalItemCount) {
//			mStart = firstVisibleItem;
//			mEnd = firstVisibleItem+visibleItemCount;
//	}
}
