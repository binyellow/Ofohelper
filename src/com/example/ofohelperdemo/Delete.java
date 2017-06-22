package com.example.ofohelperdemo;

import com.example.DB.DataOperate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Delete extends Activity{
	private Button button;
	private EditText editText;
	private String string;
	private DataOperate dataOperate;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		init();
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				string = editText.getText().toString();
				dataOperate.delete(string);
				Toast.makeText(getBaseContext(), "É¾³ý³É¹¦", Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void init(){
		button= (Button) findViewById(R.id.delete_button1);
		editText = (EditText) findViewById(R.id.delete_ET);
		dataOperate = new DataOperate(this);
	}
}
