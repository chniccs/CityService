package com.yufenit.smartcity.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.yufenit.smartcity.R;
import com.yufenit.smartcity.utils.PreferenceUtils;

/**
 * 
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.ui
 * @创建时间 2015-8-13 下午6:24:55
 * @author chniccs
 * @描述 GuideUI页面的类
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容:
 * 
 */

public class GuideUI extends Activity
{

	private Button			mGuideBtn;
	int						mDotSpace;
	private ImageView		mIvFocusDot;
	private LinearLayout	mLldots;
	private ViewPager		mVp;
	private List<View>		mData;
	int[]					images	= { R.drawable.guide_1,
									R.drawable.guide_2,
									R.drawable.guide_3 };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// 设置无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_ui);

		// 初始化view
		initView();

		// 初始化数据
		initData();

	}

	private void initData()
	{
		mData = new ArrayList<View>();

		for (int i = 0; i < images.length; i++)
		{

			ImageView iv = new ImageView(getApplicationContext());
			iv.setImageResource(images[i]);
			iv.setScaleType(ScaleType.FIT_XY);
			mData.add(iv);
		}

		mVp.setAdapter(new ViewPagerAdapter());

		for (int i = 0; i < mData.size(); i++)
		{
			ImageView iv = new ImageView(getApplicationContext());

			iv.setImageResource(R.drawable.dot_normal);

			// 通过代码设置控件的margin属性都是通过获得其父控件的参数对象来设置的。所以在这里获得linearlayout的参数对象
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
																				LayoutParams.WRAP_CONTENT);

			if (i != 0)
			{
				params.leftMargin = 10;
			}

			mLldots.addView(iv, params);
		}
	}

	private void initView()
	{
		mVp = (ViewPager) findViewById(R.id.guide_vp);
		mLldots = (LinearLayout) findViewById(R.id.guide_ll_dots);
		mIvFocusDot=(ImageView) findViewById(R.id.guide_iv_focusdot);
		mGuideBtn=(Button) findViewById(R.id.guide_btn);
		mVp.setOnPageChangeListener(new ViewPagewListener());
		
		//设置一个监听全局加载完成时的监听器
		mIvFocusDot.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout()
			{
				//在加载完成时就可以获得控件的宽高，从而获得两个点之间的距离
				mDotSpace=mLldots.getChildAt(1).getLeft()-mLldots.getChildAt(0).getLeft();
				//移除监听器
				mIvFocusDot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}

	/**
	 * 
	 * @项目名 SmartCity
	 * @包名 com.yufenit.smartcity.ui
	 * @创建时间 2015-8-13 下午6:42:20
	 * @author chniccs
	 * @描述 ViewPager的适配器类
	 * 
	 * 
	 */
	private class ViewPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			return mData.size();
		}

		// 判断是否关联
		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView(mData.get(position));
		}

		// 实例化view
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			container.addView(mData.get(position));
			return mData.get(position);
		}

	}

	private class ViewPagewListener implements OnPageChangeListener
	{
		// 当view滑动时回调方法
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{
			//position 当前view的编号
			//positionOffset 当前移动距离和整个view宽度的比值
			//positionOffsetPixels 移动的像素点
			
			// 获得要移动的相对距离
			int left=(int) (mDotSpace*positionOffset+mDotSpace*position+0.5f);
			
			RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
			                                                               LayoutParams.WRAP_CONTENT);
			params.leftMargin=left;
			
			mIvFocusDot.setLayoutParams(params);
			
		}

		// 当view被选中时回调方法
		@Override
		public void onPageSelected(int position)
		{

			//判断是否是最后一个view，如果是就显示按钮，隐藏点
			mGuideBtn.setVisibility(position==(mData.size()-1)?View.VISIBLE:View.GONE);
		}

		// 当滑动状态变化时回调方法
		@Override
		public void onPageScrollStateChanged(int state)
		{

		}

	}
	
	//跳转到首页的方法
	public void toHome(View v){
		
		Intent intent=new Intent();
		
		intent.setClass(GuideUI.this, HomeUI.class);
		
		startActivity(intent);
		
		finish();
		
		PreferenceUtils.setBoolean(getApplicationContext(), WelcomeUI.FIRST_LOAD, false);
	}
}
