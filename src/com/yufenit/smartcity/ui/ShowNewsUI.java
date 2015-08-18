package com.yufenit.smartcity.ui;

import com.yufenit.smartcity.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.ui
 * @创建时间 2015-8-18 下午10:27:38
 * @author Administrator
 * @描述 展示新闻内容的页面
 * 
 * 
 */

public class ShowNewsUI extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_show);
	}
}
