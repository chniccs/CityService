package com.yufenit.smartcity.controller.menu;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnChildClick;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
import com.yufenit.smartcity.R;
import com.yufenit.smartcity.bean.NewsMenuBean;
import com.yufenit.smartcity.bean.NewsCenterBean.NewsCenterMenuBean.NewListBean;
import com.yufenit.smartcity.bean.NewsMenuBean.NewsData;
import com.yufenit.smartcity.bean.NewsMenuBean.NewsData.NewsMenuTopicBean;
import com.yufenit.smartcity.bean.NewsMenuBean.NewsData.NewsMenuTopnewsBean;
import com.yufenit.smartcity.controller.BaseController;
import com.yufenit.smartcity.controller.news.NewsPagerController;

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

public class NewsMenuController extends BaseController
{
	@ViewInject(R.id.menu_news_vp)
	private ViewPager			mViewPager;
	@ViewInject(R.id.menu_news_ti)
	private TabPageIndicator	mIndicator;

	private List<NewListBean>	mPagerDatas;

	public NewsMenuController(Context context, List<NewListBean> children) {
		super(context);
		// 获得传过来的数据
		this.mPagerDatas = children;

	}

	@Override
	public View initView(Context context)
	{

		View view = View.inflate(mContext, R.layout.menu_news, null);

		ViewUtils.inject(this, view);

		return view;
	}

	// 加载数据
	@Override
	public void initData()
	{
		// 给ViewPager设置数据
		mViewPager.setAdapter(new MenuNewAdapter());
		// 给indicator设置viewpager
		mIndicator.setViewPager(mViewPager);
	}

	@OnClick(R.id.menu_news_arr)
	public void clickArr(View view)
	{
		// 点击向右箭头的回调方法
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	public class MenuNewAdapter extends PagerAdapter
	{

		

		@Override
		public int getCount()
		{
			if (mPagerDatas != null) { return mPagerDatas.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj)
		{
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			NewListBean bean = mPagerDatas.get(position);

			NewsPagerController controller=new NewsPagerController(mContext,bean);
			
//			//设置展示的View

			View rootView = controller.getRootView();
			container.addView(rootView);
			
			return rootView;
		}

	

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

		// 为顶部的导航设置数据
		@Override
		public CharSequence getPageTitle(int position)
		{
			if (mPagerDatas != null) { return mPagerDatas.get(position).title; }
			return super.getPageTitle(position);
		}

	}

}
