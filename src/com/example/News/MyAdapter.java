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
		mimageLoader = new ImageLoader();	//��ʼ��ImageLoader����������ظ�����
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
		if (convertView==null) {	//�����view����Ϊ�� �򴴽� ���� ֱ��ͨ��Gettag()�������
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_layout, null);	//����ʾ��ʽת����View����
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);	//��ʼ��holder����� find��ʽ�е�id
			holder.Title = (TextView) convertView.findViewById(R.id.Item_tv1);
			holder.conten = (TextView) convertView.findViewById(R.id.Item_tv2);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		//���ÿؼ�����Դ
		holder.imageView.setImageResource(R.drawable.ic_launcher);
		String url = mList.get(position).imageViewURL;
		
		holder.imageView.setTag(url);		//��ͼƬ��url��
		mimageLoader.showImageByAsyncTask(holder.imageView, url);	//����ͼƬ
		holder.Title.setText(mList.get(position).Title);		//���ñ���
		holder.conten.setText(mList.get(position).content);
		return convertView;
	}
	//ͨ���ö��� �����ظ�����View����
	class ViewHolder{
		ImageView imageView;
		TextView Title,conten;
	}
//	public void onScrollStateChanged(AbsListView view, int scrollState) {
//		if (scrollState==SCROLL_STATE_IDLE) {	//��ʾ��ʱ������ֹͣ״̬
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
