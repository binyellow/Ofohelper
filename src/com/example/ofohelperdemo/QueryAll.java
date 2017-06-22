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
		list = new ArrayList<Bicycle>();//���һ��Ҫ�ǵó�ʼ��
		
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
//	    private ArrayList<Integer> selection = new ArrayList<Integer>();		//��¼��ѡ����Ŀid  
//	    private int mCheckBoxId = 0;		//listView��Ŀ����ʽ��Ӧ��xml��Դ�ļ������������checkbox��  
//	    private String mIdColumn;	//���ݿ���id����  
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
//	                if(checkbox.isChecked()){  //�����ѡ����id���浽������
//	                    selection.add(cursor.getInt(cursor.getColumnIndex(mIdColumn)));  
//	                }  
//	                else//�����Ƴ�  
//	                {  
//	                    selection.remove(new Integer(cursor.getInt(cursor.getColumnIndex(mIdColumn))));  
//	                    Toast.makeText(getBaseContext(), "has removed " + cursor.getInt(cursor.getColumnIndex(mIdColumn)),0).show();  
//	                }  
//	            }  
//	        });  
//	          
//	        return view;  
//	    }  
//	///���ؼ���  
//	    public ArrayList<Integer> getSelectedItems()  
//	    {  
//	        return selection;  
//	    }  
//	}  
//
//
//
//	//���ã�
//
//	List<Integer> bn = XXX.getSelectedItems();  
//	for(int id : bn)  
//	{  
//	 //TODO ִ��ɾ������  
//	}
}
