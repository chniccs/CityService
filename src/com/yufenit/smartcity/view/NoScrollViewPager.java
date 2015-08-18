package com.yufenit.smartcity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.view
 * @创建时间 2015-8-14 下午8:31:42
 * @author Administrator
 * @描述 TODO
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: TODO
 * 
 */

public class NoScrollViewPager extends LazyViewPager
{

	public NoScrollViewPager(Context context) {
		super(context);


	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	//设置让ViewPager实现触摸不滑动

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		System.out.println("contentFragment点击了");
		// 让触摸不产生反馈
		return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		//使触摸事件不中断，往下传递
		return false;
	}

}
