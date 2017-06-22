package com.example.ofohelperdemo;

import java.util.ArrayList;
import java.util.List;

import com.example.Adapter.MyAdapter;
import com.example.Bean.Bicycle;
import com.example.DB.DataOperate;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class QueryAll extends Activity{
	private SimpleCursorAdapter simpleCursorAdapter;
	private DataOperate dataOperate;
	private PullToRefreshListView listView;
	private List<Bicycle>list;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.queryall);
		dataOperate = new DataOperate(this);
		listView =  (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		list = new ArrayList<Bicycle>();//这个一定要记得初始化
		
		list = dataOperate.queryAll();
		MyAdapter adapter = new MyAdapter(list, this);
		listView.setAdapter(adapter);
//		Cursor c = dataOperate.queryall();
//		String [] from = {"carnumber","password"};
//		int [] to = {R.id.carnumber,R.id.password};
//		simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_listv, c, from, to);
//		listView.setAdapter(simpleCursorAdapter);
//		View view = new View(this);
//		view.setOnLongClickListener(l)
	}
//	public class CheckBoxAdapter4TextNote extends SimpleCursorAdapter{  
//	    private ArrayList<Integer> selection = new ArrayList<Integer>();		//记录被选中条目id  
//	    private int mCheckBoxId = 0;		//listView条目的样式对应的xml资源文件名（必须包含checkbox）  
//	    private String mIdColumn;	//数据库表的id名称  
//	    public CheckBoxAdapter4TextNote(Context context, int layout, Cursor c,  
//	            String[] from, int[] to, int checkBoxId, String idColumn){  
//	        super(context, layout, c, from, to);
//	        mCheckBoxId = checkBoxId; 
//	        mIdColumn = idColumn;  
//	    }  
//	    @Override  
//	    public int getCount(){  
//	        return super.getCount();  
//	    }  
//	    @Override  
//	    public Object getItem(int position){  
//	        return super.getItem(position);  
//	    }  
//	    @Override  
//	    public long getItemId(int position){  
//	        return super.getItemId(position);  
//	    }  
//	    @Override  
//	    public View getView(final int position, View convertView, ViewGroup parent){  
//	        View view = super.getView(position, convertView, parent);  
//	        final CheckBox checkbox = (CheckBox) view.findViewById(mCheckBoxId);  
//	        checkbox.setOnClickListener(new OnClickListener(){  
//	            @Override  
//	            public void onClick(View v){  
//	                Cursor cursor = getCursor();  
//	                cursor.moveToPosition(position);  
//	                  
//	                checkbox.setChecked(checkbox.isChecked());  
//	                if(checkbox.isChecked()){  //如果被选中则将id保存到集合中
//	                    selection.add(cursor.getInt(cursor.getColumnIndex(mIdColumn)));  
//	                }  
//	                else//否则移除  
//	                {  
//	                    selection.remove(new Integer(cursor.getInt(cursor.getColumnIndex(mIdColumn))));  
//	                    Toast.makeText(getBaseContext(), "has removed " + cursor.getInt(cursor.getColumnIndex(mIdColumn)),0).show();  
//	                }  
//	            }  
//	        });  
//	          
//	        return view;  
//	    }  
//	///返回集合  
//	    public ArrayList<Integer> getSelectedItems()  
//	    {  
//	        return selection;  
//	    }  
//	}  
//
//
//
//	//调用：
//
//	List<Integer> bn = XXX.getSelectedItems();  
//	for(int id : bn)  
//	{  
//	 //TODO 执行删除操作  
//	}
}
