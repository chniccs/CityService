package com.yufenit.smartcity.fragment;

import java.util.List;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yufenit.smartcity.R;
import com.yufenit.smartcity.bean.NewsCenterBean.NewsCenterMenuBean;
import com.yufenit.smartcity.controller.BaseController;
import com.yufenit.smartcity.ui.HomeUI;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.ui
 * @创建时间 2015-8-13 下午8:59:14
 * @author Administrator
 * @描述 菜单的Fragment
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容:
 * 
 */

public class MenuFragment extends BaseFragment implements OnItemClickListener
{
	private List<BaseController>		list;
	private ListView					mListView;
	/**
	 * 接收新闻列表数据
	 */
	private List<NewsCenterMenuBean>	mData;
	private MenuListAdapter				mMenuListAdapter;
	private int							mEnable	= 0;

	/**
	 * 设置新闻数据
	 * 
	 * @param data
	 */
	public void setData(List<NewsCenterMenuBean> data)
	{
		mData = data;
		mMenuListAdapter = new MenuListAdapter();
		mListView.setAdapter(mMenuListAdapter);
	}

	@Override
	protected View initView()
	{

		View view = View.inflate(mActivity, R.layout.menu_list, null);

		mListView = (ListView) view.findViewById(R.id.lv_menu_list);

		mListView.setOnItemClickListener(this);

		return view;
	}

	/**
	 * 菜单的条目点击监听器
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	
	{
		if(mEnable==position){return;}
		
		//菜单条目切换颜色
		mEnable = position;
		mMenuListAdapter.notifyDataSetChanged();
		
		// 点击后菜单自动隐藏
		SlidingMenu slidingMenu = ((HomeUI)mActivity).getSlidingMenu();
		
		slidingMenu.toggle();
		
		// 点击之后右侧页面切换
		
		ContentFragment contentFragment = (ContentFragment) ((HomeUI)mActivity).getContentFragment();
		
		contentFragment.switchMenu(position);
		
//		contentFragment. TODO
	}

	public class MenuListAdapter extends BaseAdapter
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

			ViewHolder holder = null;
			if (convertView == null)
			{
				holder = new ViewHolder();

				convertView = View.inflate(mActivity, R.layout.item_menu, null);

				convertView.setTag(holder);

				holder.menuName = (TextView) convertView.findViewById(R.id.tv_menu_titile);

			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			NewsCenterMenuBean bean = mData.get(position);

			holder.menuName.setEnabled(position == mEnable ? true : false);

			holder.menuName.setText(bean.title);

			return convertView;
		}

	}

	class ViewHolder
	{
		TextView	menuName;
	}

}
