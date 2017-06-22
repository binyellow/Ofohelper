package com.example.Adapter;

import java.util.List;

import com.example.Bean.Bicycle;
import com.example.DB.DataOperate;
import com.example.ofohelperdemo.MainActivity;
import com.example.ofohelperdemo.R;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MyAdapter extends BaseAdapter{
	private List<Bicycle>list;
	private Context context;
	private DataOperate dataOperate;
	public MyAdapter(List<Bicycle> list,Context context){
		this.dataOperate = new DataOperate(context);
		this.list = list;
		this.context = context;
		
	}
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView !=null ?convertView:View.inflate(context, R.layout.item_listv, null);
		TextView carnumber = (TextView) view.findViewById(R.id.item_carnumber);
		TextView password = (TextView) view.findViewById(R.id.item_password);
		ImageView delete = (ImageView) view.findViewById(R.id.item_imag);
		final Bicycle a = list.get(position);
		carnumber.setText(a.getCarnumber());
		password.setText(a.getPassword());
		
		delete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						list.remove(a);
						dataOperate.delete(a.getPassword());
						notifyDataSetChanged();
					}
				};//下面是dialog
				Builder builder = new Builder(context);
				builder.setTitle("确定要删除吗？");
				builder.setPositiveButton("确定", listener);
				builder.setNegativeButton("取消", null);
				builder.show();
			}
		});
		return view;
	}
	
	

}
