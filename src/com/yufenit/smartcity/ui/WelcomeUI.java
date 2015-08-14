package com.yufenit.smartcity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.yufenit.smartcity.R;
import com.yufenit.smartcity.utils.PreferenceUtils;

/**
 * 
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.ui
 * @创建时间 2015-8-13 下午6:26:58
 * @author chniccs
 * @描述 欢迎页面
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: TODO
 *
 */
public class WelcomeUI extends Activity {
	
	private RelativeLayout mSplash;
	public static String FIRST_LOAD="first_load";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//设置无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_ui);
		
		mSplash = (RelativeLayout) findViewById(R.id.welcome);
		
		AnimationSet set=new AnimationSet(false);
		
		RotateAnimation rotate=new RotateAnimation(0, 360, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		
		ScaleAnimation scale=new ScaleAnimation(0, 1, 0, 1, 
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		
		AlphaAnimation alpha=new AlphaAnimation(0, 1);
		
		set.addAnimation(rotate);
		set.addAnimation(scale);
		set.addAnimation(alpha);
		set.setDuration(2000);
		
		mSplash.setAnimation(set);
		
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {

						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						//TODO 跳转到引导页
						
						boolean flag = PreferenceUtils.getBoolean(getApplicationContext(),FIRST_LOAD );
						Intent intent=new Intent();
						if(flag){
							
							intent.setClass(WelcomeUI.this, GuideUI.class);
						}
						else{
							
							intent.setClass(WelcomeUI.this, HomeUI.class);
						}
						startActivity(intent);
						finish();
					}
				}).start();
			}
		});
		
		
		
		
		
	}
	
	
	



}
