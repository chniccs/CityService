package com.yufenit.smartcity.controller.tab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.yufenit.smartcity.bean.NewsCenterBean;
import com.yufenit.smartcity.bean.NewsCenterBean.NewsCenterMenuBean;
import com.yufenit.smartcity.controller.BaseController;
import com.yufenit.smartcity.controller.TabController;
import com.yufenit.smartcity.controller.menu.InteractController;
import com.yufenit.smartcity.controller.menu.NewsMenuController;
import com.yufenit.smartcity.controller.menu.PicController;
import com.yufenit.smartcity.controller.menu.ToPicController;
import com.yufenit.smartcity.fragment.MenuFragment;
import com.yufenit.smartcity.ui.HomeUI;
import com.yufenit.smartcity.utils.PreferenceUtils;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.controller
 * @创建时间 2015-8-14 下午7:12:18
 * @author Administrator
 * @描述 新闻中心的控制器
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容:
 * 
 */

public class NewsController extends TabController
{
	private static final String	CACHE	= "have_cache";
	/**
	 * 菜单的控制器
	 */
	protected List<BaseController>		mMenuController;
	/**
	 * 菜单的数据
	 */
	protected List<NewsCenterMenuBean>	mMenuData;
	/**
	 * 显示内容的预置空间
	 */
	private FrameLayout					mContainer;
	
	private String	result;
	private String	cache;

	public NewsController(Context context) {
		super(context);
	}

	@Override
	public View initContentView(Context context)
	{
		mContainer = new FrameLayout(context);

		return mContainer;
	}

	@Override
	public void initData()
	{
		mTvTitle.setText("新闻");
		
		cache = PreferenceUtils.getString(mContext, CACHE);
		//TODO 添加时间点来判断更新时间
		if(!TextUtils.isEmpty(cache)){
			result=cache;
			processJson(result);
		}

		String url = "http://188.188.2.87:8080/zhbj/categories.json";

		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, url, null, new RequestCallBack<String>() {

			

			@Override
			public void onFailure(HttpException e, String msg)
			{
				e.printStackTrace();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				result = responseInfo.result;
				// 解析数据
				processJson(result);
				
				PreferenceUtils.setString(mContext, CACHE, result);
			}
		});

	}

	// 解析JSON数据的方法
	public void processJson(String json)
	{

		Gson gson = new Gson();

		NewsCenterBean bean = gson.fromJson(json, NewsCenterBean.class);

		// 获得菜单的数据
		mMenuData = bean.data;

		MenuFragment menuFragment = (MenuFragment) ((HomeUI) mContext).getMenuFragment();

		menuFragment.setData(mMenuData);

		// 给菜单内容设置控制器
		mMenuController=new ArrayList<BaseController>();
		for (int i = 0; i < mMenuData.size(); i++)
		{
			NewsCenterMenuBean data = mMenuData.get(i);
			BaseController controller = null;

			switch (data.type)
			{
				case 1:
					// 新闻
					controller = new NewsMenuController(mContext,data.children);
					break;
				case 10:
					// 专题
					controller = new ToPicController(mContext);
					break;
				case 2:
					// 组图
					controller = new PicController(mContext);
					break;
				case 3:
					// 互动
					controller = new InteractController(mContext);
					break;

			}

			mMenuController.add(controller);
		}

		// 设置默认显示第0个
		switchMenu(0);

	}

	@Override
	public void switchMenu(int position)
	{
		// 清空之前的显示
		mContainer.removeAllViews();
		BaseController controller = mMenuController.get(position);
		mContainer.addView(controller.getRootView());

		// 设置顶部标题
		NewsCenterMenuBean bean = mMenuData.get(position);

		mTvTitle.setText(bean.title);

		// 加载数据
		controller.initData();

	}

}
