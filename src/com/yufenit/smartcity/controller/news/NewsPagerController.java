package com.yufenit.smartcity.controller.news;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yufenit.smartcity.R;
import com.yufenit.smartcity.bean.NewsCenterBean.NewsCenterMenuBean.NewListBean;
import com.yufenit.smartcity.bean.NewsMenuBean;
import com.yufenit.smartcity.bean.NewsMenuBean.NewsData;
import com.yufenit.smartcity.bean.NewsMenuBean.NewsData.NewsMenuTopnewsBean;
import com.yufenit.smartcity.controller.BaseController;
import com.yufenit.smartcity.controller.menu.NewsMenuController;
import com.yufenit.smartcity.controller.menu.NewsMenuController.OnIDLEeListener;
import com.yufenit.smartcity.view.TouchedViewPager;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.controller.news
 * @创建时间 2015-8-16 下午7:15:46
 * @author Administrator
 * @描述 新闻内容的控制器类
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容:
 * 
 */

public class NewsPagerController extends BaseController implements  OnIDLEeListener
{
	protected static final String		TAG		= "NewsPagerController";

	private NewsData					mData;

	private NewListBean					mBean;

	private BitmapUtils					mBitmapUtils;

	public static boolean				isFirst	= false;

	private List<NewsMenuTopnewsBean>	mPagerData;
	// 动态点的容器
	@ViewInject(R.id.news_list_point_container)
	private LinearLayout				mLlDots;
	// 顶部轮播图的容器
	@ViewInject(R.id.newsPager_top_newspic)
	private TouchedViewPager			mViewPager;
	// 顶部图片标题
	@ViewInject(R.id.news_list_tv_title)
	private TextView					mTvTopTitle;

	private NewsPagerAdapter			myAdapter;

	// 控制顶部图片切换的对象
	private PicChange					mPicChange;

	public NewsPagerController(Context context) {
		super(context);
	}

	public NewsPagerController(Context mContext, NewListBean bean) {
		super(mContext);
		this.mBean = bean;
		initData();
	}

	@Override
	public View initView(Context context)
	{

		View topView = View.inflate(mContext, R.layout.top_newspic, null);

		// 将此view注入到主view中
		ViewUtils.inject(this, topView);

		mBitmapUtils = new BitmapUtils(mContext);

		return topView;
	}

	@Override
	public void initData()
	{
		// 连接网络获得数据

		String url = mBean.url;

		url = "http://188.188.2.87:8080/zhbj/" + url;

		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException e, String msg)
			{
				System.out.println("连接失败");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String result = responseInfo.result;
				processJson(result);
			}

		});

	}

	// 解析JSON数据
	public void processJson(String json)
	{

		Gson gson = new Gson();

		NewsMenuBean bean = gson.fromJson(json, NewsMenuBean.class);

		mData = bean.data;

		mPagerData = mData.topnews;

		myAdapter = new NewsPagerAdapter();
		mViewPager.setAdapter(myAdapter);
		// 动态的加载点
		DotChange();

		NewsMenuTopnewsBean firstTime = mPagerData.get(0);
		mTvTopTitle.setText(firstTime.title);
		if(mPicChange==null){
			mPicChange = new PicChange();
			
		}
		mPicChange.start();
		
		// 设置页面切换时的触摸事件
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// super();
				int action = event.getAction();
				switch (action)
				{
					case MotionEvent.ACTION_DOWN:
						mPicChange.stop();

						break;
					case MotionEvent.ACTION_UP:
						mPicChange.start();
						break;
					case MotionEvent.ACTION_CANCEL:
//						mPicChange.start();
						//此状态是当父类控件抢夺了此控件的焦点时
						break;

					default:
						break;
				}

				return false;
			}
		});

		// 设置页面切换的监听器
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position)
			{
				NewsMenuTopnewsBean bean = mPagerData.get(position);
				mTvTopTitle.setText(bean.title);
				DotChange();
				// mPicChange.start();
				if (position == 0)
				{
					isFirst = true;
				}
				else
				{
					isFirst = false;
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{

			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});
	}

	/**
	 * 动态的加载点的方法
	 */
	private void DotChange()
	{
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,

																			LinearLayout.LayoutParams.WRAP_CONTENT);

		mLlDots.removeAllViews();

		int currentItem = mViewPager.getCurrentItem();
		params.leftMargin = 8;
		for (int i = 0; i < mPagerData.size(); i++)
		{
			ImageView iv = new ImageView(mContext);

			iv.setImageResource(R.drawable.dot_normal);

			if (i == currentItem)
			{
				iv.setImageResource(R.drawable.dot_focus);
			}
			mLlDots.addView(iv, params);
		}
	}

	public class PicChange extends Handler implements Runnable
	{

		@Override
		public void run()
		{
			int item = mViewPager.getCurrentItem();
			mViewPager.setCurrentItem(++item % (mPagerData.size()));
			postDelayed(this, 2000);

		}

		public void start()
		{

			stop();
			postDelayed(this, 2000);
//			System.out.println("开启图片切换");
		}

		public void stop()
		{
//			System.out.println("停止");
			removeCallbacks(this);
		}

	}
	


	private class NewsPagerAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mPagerData != null) { return mPagerData.size(); }
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{

			NewsMenuTopnewsBean bean = mPagerData.get(position);

			ImageView iv = new ImageView(mContext);

			mBitmapUtils.display(iv, bean.topimage);

			iv.setScaleType(ScaleType.FIT_XY);

			container.addView(iv);

			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

	}




	@Override
	public void onIDLE()
	{
		// TODO Auto-generated method stub
		mPicChange.start();
		System.out.println("接收到闲置");
	}

}
