package com.example.DB;

import java.util.ArrayList;
import java.util.List;

import com.example.Bean.Bicycle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DataOperate {
	
	private SQLiteDatabase db;
	//�ڹ��캯�������½����ݿⲢ���һ���ƶ�д�����ݿ����
	public DataOperate(Context context){
		DatabaseInit databaseInit = new DatabaseInit(context, "data.db");
		db = databaseInit.getWritableDatabase();
	}
	//��
	public void add(Bicycle bicycle){
		String sql = "insert into data (carnumber,password) values (?,?)";
		db.execSQL(sql, new Object[]{bicycle.getCarnumber(),bicycle.getPassword()});
	}
	//ɾ
	public void delete(String password){
		//ɾ��ĳ�������Ӧ��һ��
//		String sql = "delete from data where _id= ?";
		db.delete("data", "password =?", new String[] {password});
		db.close();
	}
	//��
	public void updata(String carnumber,String password){
		//����ָ�����ƺŵ�����
		String sql = "update data set password = ? where carnumber = ?";
		db.execSQL(sql, new Object[]{password,carnumber});
	}
	//��ָ�����ƺŵ��������뼯��
	public List<String> query(String carnumber,Context context){
		List<String> password1 = null;
		String sql = "select carnumber from data";
		Cursor cursor = db.rawQuery(sql, new String[]{carnumber});
		while(cursor.moveToNext()){
			String password = "password";
			String j = cursor.getString(cursor.getColumnIndex(carnumber));
			//���ݳ��ƺŲ������룡������������������������
			if(j.equals("13")){
				password1 = new ArrayList<String>();
				password1.add(j);
			}
		}
		
		return password1;
	}
	public List<Bicycle> queryAll(){
		Cursor cursor = db.query("data", null, null, null, null, null,"carnumber DESC");//��data�������carnumber
		List<Bicycle> list = new ArrayList<Bicycle>();
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("_id"));
			String carnumber = cursor.getString(1);
			String password = cursor.getString(2);
			list.add(new Bicycle(carnumber, password));
			
		}
		return list;
	}
	//������
	public Cursor queryall(){
		String sql = "select * from data";
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}
	public void close(){
		db.close();
		
	}
}
