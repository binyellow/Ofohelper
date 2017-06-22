package com.example.ofohelperdemo;

import java.util.ArrayList;
import java.util.List;

import com.example.DB.DataOperate;
import com.example.DB.DatabaseInit;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Query extends Activity {
	private DataOperate dataOperate;
	private DatabaseInit databaseInit;
	private List<String> password1;
	private EditText query_ET;
	private Cursor cursor;
	private String carnumber;
	private ListView listView;
	private Button button;
	private ArrayAdapter<String> arrayAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		init();

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String number = query_ET.getText().toString().trim();
				cursor = dataOperate.queryall();
				password1.clear();	//每次查找数据时重置数组，避免查到上次的数据
				while (cursor.moveToNext()) {
					if (cursor != null) {
						String password = "password";
						String j = cursor.getString(cursor
								.getColumnIndex("carnumber"));
						// 根据车牌号查找密码！！！！！！！！！！！！！判断值是否相等用equals方法
						if (j.equals(number)) {
							// 传指针的时候，如果没在对应活动的oncreate方法要注意用getbasecontext()方法
							// Toast.makeText(getBaseContext(),cursor.getString(cursor.getColumnIndex(password)),Toast.LENGTH_SHORT).show();
							password1.add(cursor.getString(cursor
									.getColumnIndex(password)));
						}
					}
				}
				// password1 = dataOperate.query("13", this);
				arrayAdapter = new ArrayAdapter<String>(getBaseContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, password1);
				listView.setAdapter(arrayAdapter);
				query_ET.setText("");
			}
		});
	}

	public void init() {
		password1 = new ArrayList<String>();
		query_ET = (EditText) findViewById(R.id.query_ET);
		carnumber = query_ET.getText().toString().trim();
		dataOperate = new DataOperate(this);
		// databaseInit = new DatabaseInit(this, "data.db");
		listView = (ListView) findViewById(R.id.query_listView);
		button = (Button) findViewById(R.id.query_find);
	}

}
