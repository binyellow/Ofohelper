package com.example.StartAnima;

import com.example.ofohelperdemo.R;
import com.example.ofohelperdemo.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Main_translate extends Activity{
	private ImageView right,left;
	private TextView textView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation);
		animationinit();
		new Handler().postDelayed(new Runnable() {
			public void run() {	
				Intent intent = new Intent(getBaseContext(), com.example.ofohelperdemo.MainActivity.class);
				startActivity(intent);
				Main_translate.this.finish();
			}
		}, 2500);
	}
	private void animationinit() {
		left = (ImageView) findViewById(R.id.imageViewL);
		right = (ImageView) findViewById(R.id.imageViewR);
		textView = (TextView) findViewById(R.id.textView11);
		
		AnimationSet animationSet = new AnimationSet(true);
		//1、确定类型 2、x的起始位置 3、x的模式 4、x的结束位置 5、y的和x一样
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation.setDuration(1500);
		animationSet.setStartOffset(1000);
		animationSet.addAnimation(translateAnimation);
		animationSet.setFillAfter(true);
		left.setAnimation(animationSet);
		
		AnimationSet animationSet1 = new AnimationSet(true);
		TranslateAnimation translateAnimation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
		translateAnimation1.setDuration(1500);
		animationSet1.setStartOffset(1000);
		animationSet1.addAnimation(translateAnimation1);
		animationSet1.setFillAfter(true);
		right.setAnimation(animationSet1);
		
		AnimationSet anim2 = new AnimationSet(true);
		ScaleAnimation myscaleanim = new ScaleAnimation(1f,3f,1f,3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		myscaleanim.setDuration(2000);		
		AlphaAnimation myalphaanim = new AlphaAnimation(1,0.0001f);
		myalphaanim.setDuration(2000);		
		anim2.addAnimation(myscaleanim);
		anim2.addAnimation(myalphaanim);		
		anim2.setFillAfter(true);
		textView.startAnimation(anim2);
	}
}
