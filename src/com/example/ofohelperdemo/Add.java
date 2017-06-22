package com.example.ofohelperdemo;

import com.example.Bean.Bicycle;
import com.example.DB.DataOperate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Add extends Activity {
	private Bicycle bicycle;
	private DataOperate dataOperate;
	private Button button1;
	private EditText ETcarnumber, ETpassword;
	String carnumber, password;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		init();
		// 调用监听事件 要注意导包的问题
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// for (int i = 0; i < 30; i++) {
				// bicycle = new Bicycle((i + 2) + "", (i + 3) + "");
				// dataOperate.add(bicycle);
				// }
				// bicycle = new Bicycle(1405040114 + "", 1405040113 + "");
				// dataOperate.add(bicycle);

				carnumber = ETcarnumber.getText().toString().trim();
				password = ETpassword.getText().toString().trim();
				if (carnumber.length()>4 && password.length()>3) {	//判断是否输入了数据
					bicycle = new Bicycle(carnumber, password);
					dataOperate.add(bicycle);
					Toast.makeText(getBaseContext(), "添加成功", Toast.LENGTH_SHORT)
							.show();
					ETcarnumber.setText("");
					ETpassword.setText("");
				}else {
					Toast.makeText(getBaseContext(), "请输入有效数据", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void init() {
		dataOperate = new DataOperate(this);
		button1 = (Button) findViewById(R.id.add_button);
		ETcarnumber = (EditText) findViewById(R.id.add_carnumber);
		ETpassword = (EditText) findViewById(R.id.add_password);
	}
}
