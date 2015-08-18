package com.yufenit.smartcity.controller.menu;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.yufenit.smartcity.bean.NewsMenuBean;
import com.yufenit.smartcity.bean.NewsMenuBean.NewsData.NewsMenuNewsBean;
import com.yufenit.smartcity.controller.BaseController;
import com.yufenit.smartcity.controller.tab.NewsController.onChangeClickListener;
import com.yufenit.smartcity.utils.Constants;

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

public class PicController extends BaseController implements onChangeClickListener
{

	@ViewInject(R.id.pics_listView)
	private ListView				mListView;
	@ViewInject(R.id.pics_gridView)
	private GridView				mGridView;

	private BitmapUtils				mBitmapUtils;
	private List<NewsMenuNewsBean>	mData;
	private boolean	flag=false;

	public PicController(Context context) {
		super(context);
		mBitmapUtils = new BitmapUtils(context);
	}

	@Override
	public View initView(Context context)
	{

		View view = View.inflate(mContext, R.layout.pics_list, null);

		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData()
	{

		String url = Constants.PHOTO_URL;

		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException e, String msg)
			{
				// 连接失败

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// 连接成功
				String result = responseInfo.result;
				processJson(result);

			}
		});
	}

	public void processJson(String json)
	{

		Gson gson = new Gson();

		NewsMenuBean bean = gson.fromJson(json, NewsMenuBean.class);

		mData = bean.data.news;

		PicListAdapter adapter = new PicListAdapter();
		
		mListView.setAdapter(adapter);

		mGridView.setAdapter(adapter);
	}

	public class PicListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			if (mData != null) { return mData.size(); }
			return 0;
		}

		@Override
		public Object getItem(int position)
		{
			if (mData != null) { return mData.get(position); }
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

				convertView = View.inflate(mContext, R.layout.item_pics, null);

				convertView.setTag(holder);

				holder.img = (ImageView) convertView.findViewById(R.id.pic_list_img);

				holder.titile = (TextView) convertView.findViewById(R.id.pic_list_title);

			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			NewsMenuNewsBean bean = mData.get(position);

			holder.titile.setText(bean.title);

			mBitmapUtils.display(holder.img, bean.listimage);

			return convertView;
		}

	}

	class ViewHolder
	{

		public TextView		titile;
		public ImageView	img;
	}

	@Override
	public void click()
	{
		flag = !flag;
		
		if(flag){
			mListView.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
		}else{
			mListView.setVisibility(View.VISIBLE);
			mGridView.setVisibility(View.GONE);
		}
		
	}

}
