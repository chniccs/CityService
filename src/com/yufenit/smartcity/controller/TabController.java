package com.yufenit.smartcity.controller;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yufenit.smartcity.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.yufenit.smartcity.ui.HomeUI;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.controller
 * @创建时间 2015-8-14 下午7:47:26
 * @author Administrator
 * @描述 controller的父类
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容:
 * 
 */

public abstract class TabController extends BaseController
{

	protected TextView		mTvTitle;
	protected ImageView		mIvMenu;

	private ImageView		mIvSlidemenu;

	public ImageView		mIvChangeList;

	protected FrameLayout	mContentContainer;

	public TabController(Context context) {
		super(context);
	}
	/**
	 * 控制组图中点击切换展示方式的方法
	 */
	

	@Override
	public View initView(Context context)
	{
		View view = View.inflate(context, R.layout.tab_controller, null);

		mIvMenu = (ImageView) view.findViewById(R.id.iv_tab_ctrl_menu);
		mTvTitle = (TextView) view.findViewById(R.id.tv_tab_ctrl_title);
		mIvSlidemenu = (ImageView) view.findViewById(R.id.iv_tab_ctrl_menu);
		mIvChangeList = (ImageView) view.findViewById(R.id.change_pic_list);
		
		
		
		mContentContainer = (FrameLayout) view.findViewById(R.id.tab_content_container);
		// 添加内容不同的区域到view中
		mContentContainer.addView(initContentView(context));
		// 为点击设置监听器
		mIvSlidemenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{

				SlidingMenu slidingMenu = ((HomeUI) mContext).getSlidingMenu();
				slidingMenu.toggle();
			}
		});

		return view;
	}

	/**
	 * 将不同区域交给子类去实现
	 * 
	 * @param context
	 * @return
	 */
	public abstract View initContentView(Context context);

	/**
	 * 控制切换菜单内容的方法
	 * 
	 * @param position
	 *            当前点击的菜单的位置
	 */
	public void switchMenu(int position)
	{
	};

}
