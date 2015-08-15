package com.yufenit.smartcity.controller.menu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yufenit.smartcity.controller.BaseController;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.controller.menu
 * @创建时间 2015-8-15 上午10:13:52
 * @author Administrator
 * @描述 菜单栏新闻的内容控制器
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: 
 * 
 */

public class InteractController extends BaseController
{

	public InteractController(Context context) {
		super(context);
		
	}

	@Override
	public View initView(Context context)
	{
		TextView tv=new TextView(context);
		tv.setText("互动");
		tv.setTextSize(24);
		tv.setTextColor(Color.RED);
		tv.setGravity(Gravity.CENTER);
		
		return tv;
	}

}
