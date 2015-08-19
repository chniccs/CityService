package com.yufenit.smartcity.controller.news;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
import com.yufenit.smartcity.bean.NewsMenuBean.NewsData.NewsMenuNewsBean;
import com.yufenit.smartcity.bean.NewsMenuBean.NewsData.NewsMenuTopnewsBean;
import com.yufenit.smartcity.controller.BaseController;
import com.yufenit.smartcity.controller.menu.NewsMenuController.OnIDLEeListener;
import com.yufenit.smartcity.ui.ShowNewsUI;
import com.yufenit.smartcity.utils.Constants;
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

public class NewsPagerController extends BaseController implements OnIDLEeListener, OnRefreshListener2<ListView>, OnItemClickListener
{
	protected static final String		TAG			= "NewsPagerController";

	public static final String			BASE_URL	= "http://10.0.2.2:8080/zhbj/";

	private NewsData					mData;

	private NewListBean					mBean;

	private BitmapUtils					mBitmapUtils;

	public static boolean				isFirst		= false;

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
	@ViewInject(R.id.new_listview)
	private PullToRefreshListView		mListView;

	private NewsPagerAdapter			myAdapter;

	// 控制顶部图片切换的对象
	private PicChange					mPicChange;

	private List<NewsMenuNewsBean>		mNewsData;

	private String						mMoreData;

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

		mBitmapUtils = new BitmapUtils(mContext);

		View newsList = View.inflate(mContext, R.layout.news_list, null);

		// 将此view注入到主view中
		ViewUtils.inject(this, newsList);

		View topView = View.inflate(mContext, R.layout.top_newspic, null);

		// 将此view注入到主view中
		ViewUtils.inject(this, topView);

		// 将顶部图片列表加到新闻列表的头部View
		mListView.getRefreshableView().addHeaderView(topView);
		// 设置下拉刷新的模式
		mListView.setMode(Mode.BOTH);

		mListView.setOnRefreshListener(this);

		mListView.setOnItemClickListener(this);

		return newsList;
	}

	public class NewsPagerListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{

			if (mNewsData != null) { return mNewsData.size(); }
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			if (mNewsData != null) { return mNewsData.get(position); }
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{

				holder = new ViewHolder();

				convertView = View.inflate(mContext, R.layout.item_news_list, null);

				convertView.setTag(holder);

				holder.date = (TextView) convertView.findViewById(R.id.item_news_date);
				holder.pic = (ImageView) convertView.findViewById(R.id.item_news_icon);
				holder.title = (TextView) convertView.findViewById(R.id.item_news_title);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			NewsMenuNewsBean bean = mNewsData.get(position);

			holder.title.setText(bean.title);
			holder.date.setText(bean.pubdate);
			String uri = bean.listimage;

			mBitmapUtils.display(holder.pic, uri);

			return convertView;
		}

	}

	private class ViewHolder
	{

		public TextView		title;
		public TextView		date;
		public ImageView	pic;
	}

	@Override
	public void initData()
	{
		// 连接网络获得数据

		String url = mBean.url;

		url = BASE_URL + url;

		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException e, String msg)
			{
				System.out.println("连接失败");

				mListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// System.out.println("连接成功");
				String result = responseInfo.result;
				processJson(result);

				mListView.onRefreshComplete();
			}

		});

	}

	// 解析JSON数据
	public void processJson(String json)
	{

		Gson gson = new Gson();

		NewsMenuBean bean = gson.fromJson(json, NewsMenuBean.class);

		mData = bean.data;
		// 更多数据的地址
		mMoreData = mData.more;

		mNewsData = mData.news;

		mPagerData = mData.topnews;

		myAdapter = new NewsPagerAdapter();
		mViewPager.setAdapter(myAdapter);
		// System.out.println("设置适配器");
		mListView.setAdapter(new NewsPagerListAdapter());
		// 动态的加载点
		DotChange();

		NewsMenuTopnewsBean firstTime = mPagerData.get(0);
		mTvTopTitle.setText(firstTime.title);
		if (mPicChange == null)
		{
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
						// mPicChange.start();
						// 此状态是当父类控件抢夺了此控件的焦点时
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
			// System.out.println("开启图片切换");
		}

		public void stop()
		{
			// System.out.println("停止");
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

		mPicChange.start();
		// System.out.println("接收到闲置");
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
	{
		// 下拉刷新
		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {

			@Override
			public void run()
			{
				// 连接网络获得数据

				String url = mBean.url;

				url = BASE_URL + url;

				HttpUtils utils = new HttpUtils();

				utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException e, String msg)
					{
						Toast.makeText(mContext, "连接失败", Toast.LENGTH_SHORT).show();

						mListView.onRefreshComplete();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo)
					{
						String result = responseInfo.result;

						processJson(result);

						mListView.onRefreshComplete();
					}
				});

			}
		}, 2000);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
	{
		// 上拉加载
		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {

			@Override
			public void run()
			{
				if (!TextUtils.isEmpty(mMoreData))
				{

				}

			}
		}, 2000);

	}

	// item点击的响应方法
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		System.out.println("点击了");
		Intent intent = new Intent();

		NewsMenuNewsBean bean = mNewsData.get(position);

		intent.putExtra(Constants.WEB_URL, bean.url);
		intent.putExtra(Constants.NEW_TITLE, bean.title);

		intent.setClass(mContext, ShowNewsUI.class);

		mContext.startActivity(intent);

	}

}
