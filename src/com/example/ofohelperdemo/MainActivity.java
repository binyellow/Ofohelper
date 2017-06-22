package com.example.ofohelperdemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;



import com.example.Bean.Bicycle;
import com.example.DB.DataOperate;
import com.example.DB.DatabaseInit;
import com.example.News.News_MainActivity;
import com.example.weatherbean.Location;
import com.example.weatherbean.Now;
import com.example.weatherbean.Results;
import com.example.weatherbean.Root;
import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button add, delete, updata, queryall,query;
	private Bicycle bicycle;
	private DatabaseInit databaseInit;
	private DataOperate dataOperate;
	private ImageButton searchButton,editButton,deleteButton,addButton;
	private PopupWindow mPopupWindow;
	private Boolean flag = false;
	String result_flag;
	private MenuDrawer mDrawer;
	TextView place,weather,temp;
	EditText in_cityname;
	ImageButton submit,news_button;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDrawer = MenuDrawer.attach(this,MenuDrawer.Type.BEHIND, Position.LEFT, MenuDrawer.MENU_DRAG_WINDOW);
		mDrawer.setContentView(R.layout.main_image);
		mDrawer.setMenuView(R.layout.weather);
		
		place = (TextView) findViewById(R.id.place);
        weather = (TextView) findViewById(R.id.weather);
        temp = (TextView) findViewById(R.id.temp);
        in_cityname = (EditText) findViewById(R.id.in_cityname);
        submit = (ImageButton) findViewById(R.id.submit);
        news_button = (ImageButton) findViewById(R.id.news_button);
        
        String flag;
        
        submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String city_name = in_cityname.getText().toString().trim();
//				Log.i("TAG", "111"+city_name);
				if (TextUtils.isEmpty(city_name)/*city_name.length()<1*/) {
					Log.i("TAG", "111"+city_name);
					Toast.makeText(getBaseContext(), "请先输入城市", 1).show();
				}else {
						String city_pinyin = cn2Spell(city_name);
						Log.i("TAG", "111"+city_pinyin);
						Http_weather http_weather = new Http_weather();
				        http_weather.execute(city_pinyin);  
					}
				}
		});
		news_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),News_MainActivity.class);
				startActivity(intent);
			}
		});
        
		init();
		databaseInit = new DatabaseInit(this, "data.db");
		dataOperate = new DataOperate(this);
	}
	public static String cn2Spell(String chinese) {   
        StringBuffer pybf = new StringBuffer();   
        char[] arr = chinese.toCharArray();   
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
        for (int i = 0; i < arr.length; i++) {   
                if (arr[i] > 128) {   
                        try {   
                                pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);   
                        } catch (BadHanyuPinyinOutputFormatCombination e) {   
                                e.printStackTrace();   
                        }   
                } else {   
                        pybf.append(arr[i]);   
                }   
        }   
        return pybf.toString();   
	}
	class Http_weather extends AsyncTask<String, Void, String>{
		protected String doInBackground(String... params) {
			String paramsString = params[0];
			String httpUrl = "http://apis.baidu.com/thinkpage/weather_api/currentweather";
			String httpArg = "location="+paramsString+"&language=zh-Hans&unit=c";//"location=zhuzhou&language=zh-Hans&unit=c"
			Log.i("test", httpArg);//输出用户输入要查询的城市和天数后的请求arg
			BufferedReader reader = null;
			String result = null;
			StringBuffer sbf = new StringBuffer();
			try {
				httpUrl = httpUrl + "?" + httpArg;
		        URL url = new URL(httpUrl);
		        HttpURLConnection connection = (HttpURLConnection) url
		                .openConnection();
		        connection.setRequestMethod("GET");
		        // 填入apikey到HTTP header
		        connection.setRequestProperty("apikey",  "a9160426994ac6af308dcff40581f4ba");
		        connection.connect();
		        InputStream is = connection.getInputStream();
		        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        String strRead = null;
		        while ((strRead = reader.readLine()) != null) {
		            sbf.append(strRead);
		            sbf.append("\r\n");
		        }
		        reader.close();
		        result = sbf.toString();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			Log.i("TAG", "result1:"+result);
			if(result.indexOf("300209") != -1){//判断字符串是否包含某个字符段
				result_flag = "false";
				return result_flag;
			}else {
				result_flag = "true";
				return result;
			}  
//			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result_flag=="false") {
				Toast.makeText(getBaseContext(), "不要输个不知名的城市难为银家额", 1).show();
			}else {
				Log.i("test", "result2"+result);
				if (result_flag=="true") {
					Root root = GsonUtil.parseJsonWithGson(result, Root.class);
					List<Results> results = root.getResults();
					Now now = results.get(0).getNow();
					Location location = results.get(0).getLocation();
					place.setText(location.getName());
					weather.setText(now.getText());
					temp.setText(now.getTemperature()+"℃");
				}
			}
		}
		
    }
    static class GsonUtil {
        //将Json数据解析成相应的映射对象
        public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
            Gson gson = new Gson();
            T result = gson.fromJson(jsonData, type);
            return result;
        }
    }
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_image:
//			for(int i = 0;i<30;i++){
//				bicycle = new Bicycle((i+2)+"", (i+3)+"");
//				dataOperate.add(bicycle);
//			}
//			bicycle = new Bicycle(1405040113+"", 1+"");
//			dataOperate.add(bicycle);
			Intent addIntent = new Intent(MainActivity.this,Add.class);
			startActivity(addIntent);
			break;
		case R.id.delete_image:
//			bicycle = new Bicycle();
//			dataOperate.delete(12);
			Intent deleteIntent = new Intent(MainActivity.this,Delete.class);
			startActivity(deleteIntent);
			break;
		case R.id.edit_image:
//			bicycle = new Bicycle();
//			dataOperate.updata(10,0);
			Intent updaIntent = new Intent(MainActivity.this,Update.class);
			startActivity(updaIntent);
			break;
//		case R.id.query:
//			Intent queryintent = new Intent(MainActivity.this,Query.class);
//			startActivity(queryintent);
//			Cursor cursor = dataOperate.queryall();
//			while(cursor.moveToNext()){
//				String carnumber = "carnumber";
//				String password = "password";
//				String j = cursor.getString(cursor.getColumnIndex(carnumber));
//				//根据车牌号查找密码！！！！！！！！！！！！！
//				if(j.equals("13")){
//					Toast.makeText(this, cursor.getString(cursor.getColumnIndex(password)), Toast.LENGTH_SHORT).show();
//				}
//			}
//			break;
		case R.id.search_image :
			if(flag == false){
				ShowPopWindow();
				flag = true;
			}else {
				mPopupWindow.dismiss();
				flag = false;
			}
//			Intent queryallintent = new Intent(MainActivity.this,QueryAll.class);
//			startActivity(queryallintent);
			break;		
				
//				String [] colunames= cursor.getColumnNames();	
//				for (String columnname : colunames) {
//					String i =cursor.getString(1);	//获得第一列的ID值
//					String carnumber = "carnumber";
//					String j = cursor.getString(cursor.getColumnIndex(columnname));
//					Toast.makeText(this, j, Toast.LENGTH_SHORT).show();
//				}
//			}
			
		default:
			break;
		}
	}

	private void ShowPopWindow() {
		View popView = LayoutInflater.from(this).inflate(R.layout.popwindow, null);
		mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		//显示popwindow
//		View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.main, null);  //要将他在main中显示所以要把main座位他的父容器
//		mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
		TextView pop_TV_All = (TextView) popView.findViewById(R.id.pop_TV_All);	//因为这个textview不在main.xml中药初始化就要找到其父布局
		TextView pop_TV_find = (TextView) popView.findViewById(R.id.pop_TV_Find);
		
		pop_TV_All.setOnClickListener(this);
		pop_TV_find.setOnClickListener(this);
		mPopupWindow.showAsDropDown(searchButton);
		pop_TV_All.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent queryallintent = new Intent(MainActivity.this,
						QueryAll.class);
				startActivity(queryallintent);
				mPopupWindow.dismiss(); 
			}
		});
		pop_TV_find.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent queryintent = new Intent(MainActivity.this, Query.class);
				startActivity(queryintent);
				mPopupWindow.dismiss(); 
			}
		});
		
	}

	public void init() {
//		add = (Button) findViewById(R.id.add);
//		delete = (Button) findViewById(R.id.delete);
//		updata = (Button) findViewById(R.id.update);
//		queryall = (Button) findViewById(R.id.queryall);
//		query = (Button) findViewById(R.id.query);
		

		addButton = (ImageButton) findViewById(R.id.add_image);
		deleteButton = (ImageButton) findViewById(R.id.delete_image);
		editButton = (ImageButton) findViewById(R.id.edit_image);
		searchButton = (ImageButton) findViewById(R.id.search_image);
		
		addButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		editButton.setOnClickListener(this);
		searchButton.setOnClickListener(this);
	}
}
