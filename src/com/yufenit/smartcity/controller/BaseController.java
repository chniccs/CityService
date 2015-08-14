package com.yufenit.smartcity.controller;

import android.content.Context;
import android.view.View;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.controller
 * @创建时间 2015-8-14 下午6:58:21
 * @author Administrator
 * @描述  controller基类
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: 
 * 
 */

public abstract class BaseController
{
	protected View mRootView;
	
	protected Context mContext;
	
	/**
	 * 构造方法，实例view
	 */
	public BaseController(Context context){
		mContext=context;
		mRootView=initView(context);
	}
	
	/**
	 * 返回View对象
	 * @return
	 */

	public View getRootView()
	{
		return mRootView;
	}
	/**
	 * 让子类去实现view
	 * @return
	 */
	public abstract View initView(Context context);
	
	/**
	 * 初始化数据
	 */
	public void initData(){
		
	}

}
