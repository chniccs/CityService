package com.yufenit.smartcity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.ui
 * @创建时间 2015-8-13 下午8:59:14
 * @author Administrator
 * @描述 菜单的Fragment
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: 
 * 
 */

public class HomeMenuFragment extends BaseFragment
{



	@Override
	protected View initView()
	{
		TextView tv=new TextView(mActivity);
		tv.setText("test");
		tv.setTextColor(Color.WHITE);
		
		return tv;
	}
}
