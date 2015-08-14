package com.yufenit.smartcity.controller.tab;

import com.yufenit.smartcity.controller.TabController;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.controller
 * @创建时间 2015-8-14 下午7:12:18
 * @author Administrator
 * @描述 TODO
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: TODO
 * 
 */

public class ServiceController extends TabController
{

	public ServiceController(Context context) {
		super(context);
	}



	@Override
	public View initContentView(Context context)
	{
		
		TextView tv=new TextView(context);
		tv.setText("服务");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
	@Override
	public void initData()
	{
		mTvTitle.setText("服务");
	}

}
