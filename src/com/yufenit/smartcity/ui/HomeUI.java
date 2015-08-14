package com.yufenit.smartcity.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.yufenit.smartcity.R;
import com.yufenit.smartcity.fragment.HomeContentFragment;
import com.yufenit.smartcity.fragment.HomeMenuFragment;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.ui
 * @创建时间 2015-8-13 下午8:34:28
 * @author Administrator
 * @描述 主页
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容:
 * 
 */

public class HomeUI extends SlidingFragmentActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//给内容设置布局
		setContentView(R.layout.home_content_ui);
		//给菜单设置布局
		setBehindContentView(R.layout.home_menu_ui);
		//获得滑动菜单对象
		SlidingMenu slidingMenu = getSlidingMenu();
		//设置菜单宽
		slidingMenu.setBehindWidth(150);
		//设置触摸模式
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 加载Fragment
		initView();
	}

	private void initView()
	{

		FragmentManager fm = getSupportFragmentManager();

		FragmentTransaction transaction = fm.beginTransaction();

		// 加载菜单

		transaction.replace(R.id.home_menu, new HomeMenuFragment());
		// 加载内容
		transaction.replace(R.id.home_content, new HomeContentFragment());

		transaction.commit();
	}

}
