package com.yufenit.smartcity.controller.menu;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
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
import com.yufenit.smartcity.ui.HomeUI;

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

public class NewsMenuController extends BaseController implements OnClosedListener, OnOpenedListener, OnCloseListener, OnOpenListener
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
		
		// 获得菜单控制对象
		final SlidingMenu slidingMenu = ((HomeUI) mContext).getSlidingMenu();
//为侧滑菜单设置监听器，用于告诉子控件当前菜单的状态
		slidingMenu.setOnClosedListener(this);
		slidingMenu.setOnOpenedListener(this);
		slidingMenu.setOnCloseListener(this);
		slidingMenu.setOnOpenListener(this);
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position)
			{
				int currentItem = mViewPager.getCurrentItem();

				if (currentItem == 0&&NewsPagerController.isFirst)
				{
					//Log.d(TAG, "可拉出菜单");
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}
				else
				{
					//Log.d(TAG, "不可拉出菜单");
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
			}
			
			@Override
			public void onPageScrollStateChanged(int state)
			{
				
				if(state==ViewPager.SCROLL_STATE_IDLE){
					noticefyUpdate();
					System.out.println("通知更新");
				}
			}
		});
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
			
			//将controller作为标记添加到rootView中以便在destroyItem方法中取出
			rootView.setTag(controller);
			
			//添加到容器中
			container.addView(rootView);
			
			//实现接口来进行两个类之间通信
			addOnIDLEeListener(controller);
			
			
			return rootView;
		}

	

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
			//通过标记取得controller对象
			NewsPagerController controller = (NewsPagerController) (((View)object).getTag());
			//移除
			removeOnIDLEeListener(controller);
		}

		// 为顶部的导航设置数据
		@Override
		public CharSequence getPageTitle(int position)
		{
			if (mPagerDatas != null) { return mPagerDatas.get(position).title; }
			return super.getPageTitle(position);
		}

	}
	/**
	 * 
	 * @项目名 SmartCity
	 * @包名 com.yufenit.smartcity.controller.menu
	 * @创建时间 2015-8-17 下午7:45:04
	 * @author chniccs
	 * @描述 监听本控制器是的ViewPager是否闲置状态的监听器接口
	 * 
	 *
	 */
	public interface OnIDLEeListener{
		
		void onIDLE();
	}
	//观察者模式
	private  List<OnIDLEeListener> mListener=new  LinkedList<NewsMenuController.OnIDLEeListener>();
	
	public void addOnIDLEeListener(OnIDLEeListener listener){
		
		if(mListener!=null&&!mListener.contains(listener)){
			
			mListener.add(listener);
		}
	}
	//通知更新
	public void noticefyUpdate(){
		//此处用迭代器实现，其是线程安全的遍历方式，因为在遍历过程中可能会出现集合元素的添加或删除。
		Iterator<OnIDLEeListener> iterator = mListener.iterator();
		
		while(iterator.hasNext()){
			
			OnIDLEeListener next = iterator.next();
			//回调方法
			next.onIDLE();
		}
		
	}
	public void removeOnIDLEeListener(OnIDLEeListener listener){
		
		if(mListener!=null){
			
			mListener.remove(listener);
		}
	}

	@Override
	public void onOpen()
	{
		// SlidingMenu的监听器实现方法，用于向子控件传递SlidingMenu的状态，下同
		noticefyUpdate();
	}

	@Override
	public void onClose()
	{
		
		noticefyUpdate();
	}

	@Override
	public void onOpened()
	{
		noticefyUpdate();
		
	}

	@Override
	public void onClosed()
	{
		noticefyUpdate();
		
	}
	

}
