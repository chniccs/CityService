package com.yufenit.smartcity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.fragment
 * @创建时间 2015-8-13 下午9:37:42
 * @author Administrator
 * @描述 基类Fragment
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: TODO
 * 
 */

public abstract class BaseFragment extends Fragment
{

	protected FragmentActivity	mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mActivity = getActivity();
	}

	// 加载view
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return initView();
	}

	// 加载数据
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{

		super.onActivityCreated(savedInstanceState);

		initData();
	}

	protected abstract View initView();

	protected void initData()
	{

	}
}
