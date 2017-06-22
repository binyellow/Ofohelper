package com.example.ofohelperdemo;

import com.example.DB.DataOperate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Update extends Activity{
//	private Bicycle bicycle;
	private EditText ETnumber,ETpassword;
	private Button button;
	private DataOperate dataOperate;
	String carnumber,password;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		init();
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				carnumber = ETnumber.getText().toString().trim();
				password = ETpassword.getText().toString().trim();
				dataOperate.updata(carnumber,password);
				Toast.makeText(getBaseContext(), "ÐÞ¸Ä³É¹¦", Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void init(){
		ETnumber = (EditText) findViewById(R.id.update_ETnumber);
		ETpassword = (EditText) findViewById(R.id.update_ETpassword);
		button = (Button) findViewById(R.id.update_button);
		dataOperate = new DataOperate(this);
	}
}
