package com.yufenit.smartcity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yufenit.smartcity.R;
import com.yufenit.smartcity.controller.BaseController;
import com.yufenit.smartcity.controller.TabController;
import com.yufenit.smartcity.controller.tab.GovController;
import com.yufenit.smartcity.controller.tab.HomeController;
import com.yufenit.smartcity.controller.tab.NewsController;
import com.yufenit.smartcity.controller.tab.ServiceController;
import com.yufenit.smartcity.controller.tab.SettingController;
import com.yufenit.smartcity.ui.HomeUI;
import com.yufenit.smartcity.view.MyViewPager;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.ui
 * @创建时间 2015-8-13 下午8:59:14
 * @author Administrator
 * @描述 首页内容的fragment
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容:
 * 
 */

public class ContentFragment extends BaseFragment
{

	private RadioGroup				mRadioGroup;
	private MyViewPager				mViewPager;
	private List<BaseController>	mPagerData;
	
	private int mCurrentTab;

	@Override
	protected View initView()
	{
		View view = View.inflate(mActivity, R.layout.fra_content, null);

		mRadioGroup = (RadioGroup) view.findViewById(R.id.content_rg);
		mViewPager = (MyViewPager) view.findViewById(R.id.content_menu_vp);
		//设置默认显示的页面
		mRadioGroup.check(R.id.home_btn_home);
		//设置RadioButton被点击的监听器
		mRadioGroup.setOnCheckedChangeListener(new RadioGroupListener());

		slidingMenuShow(false);

		return view;
	}

	/**
	 * 
	 * @项目名 SmartCity
	 * @包名 com.yufenit.smartcity.fragment
	 * @创建时间 2015-8-14 下午8:16:12
	 * @author chniccs
	 * @描述 RadioGroup的子控件被点击的监听器
	 * 
	 * @SVN版本号 $Rev$
	 * @修改人 $Author$
	 * @修改时间: $Date$
	 * @修改的内容:
	 * 
	 */
	public class RadioGroupListener implements OnCheckedChangeListener
	{

		private static final int	BTN_HOME	= 0;
		private static final int	BTN_NEWS	= 1;
		private static final int	BTN_SERVICE	= 2;
		private static final int	BTN_GOV		= 3;
		private static final int	BTN_SETTING	= 4;

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			
			switch (checkedId)
			{
				case R.id.home_btn_home:
					slidingMenuShow(false);
					mViewPager.setCurrentItem(BTN_HOME);
					mCurrentTab=0;
					break;
				case R.id.home_btn_news:
					slidingMenuShow(true);
					mViewPager.setCurrentItem(BTN_NEWS);
					mCurrentTab=1;
					break;
				case R.id.home_btn_service:
					slidingMenuShow(true);
					mViewPager.setCurrentItem(BTN_SERVICE);
					mCurrentTab=2;
					break;
				case R.id.home_btn_gov:
					slidingMenuShow(true);
					mViewPager.setCurrentItem(BTN_GOV);
					mCurrentTab=3;
					break;
				case R.id.home_btn_setting:
					slidingMenuShow(false);
					mViewPager.setCurrentItem(BTN_SETTING);
					mCurrentTab=4;
					break;

			}

		}

	}

	private void slidingMenuShow(boolean isShow)
	{

		// mActivity是主页面的对象的引用，所以这里可以将其强转为主页面类的对象，从而来调用方法获得侧滑菜单管理对象
		SlidingMenu slidingMenu = ((HomeUI) mActivity).getSlidingMenu();

		slidingMenu.setTouchModeAbove(isShow ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
	}

	// 加载数据
	@Override
	protected void initData()
	{
		
		mPagerData = new ArrayList<BaseController>();
		mPagerData.add(new HomeController(mActivity));
		mPagerData.add(new NewsController(mActivity));
		mPagerData.add(new ServiceController(mActivity));
		mPagerData.add(new GovController(mActivity));
		mPagerData.add(new SettingController(mActivity));

		mViewPager.setAdapter(new MyPagerAdapter());

	}

	public class MyPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			return mPagerData.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj)
		{
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{

			BaseController controller = mPagerData.get(position);

			View view = controller.getRootView();

			// 加载完view后触发加载数据
			controller.initData();

			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView(mPagerData.get(position).getRootView());
		}

	}

	public void switchMenu(int position)
	{
		//首先获得当前显示的是哪个View
		TabController controller = (TabController) mPagerData.get(mCurrentTab);
		//调用对应控制器的控制菜单的方法
		controller.switchMenu(position);
		
	}
}
