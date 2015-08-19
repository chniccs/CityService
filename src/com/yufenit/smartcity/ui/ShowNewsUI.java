package com.yufenit.smartcity.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yufenit.smartcity.R;
import com.yufenit.smartcity.utils.Constants;
import com.yufenit.smartcity.utils.PreferenceUtils;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.ui
 * @创建时间 2015-8-18 下午10:27:38
 * @author Administrator
 * @描述 展示新闻内容的页面
 * 
 * 
 */

public class ShowNewsUI extends Activity
{

	@ViewInject(R.id.news_show_textsize)
	private ImageView	mIvTextSize;

	@ViewInject(R.id.news_show_share)
	private ImageView	mIvShare;

	@ViewInject(R.id.news_show_wb)
	private WebView		mWvWeb;

	@ViewInject(R.id.news_show_title)
	private TextView	mTvTitle;

	private int	checkedItem=2;

	@OnClick(R.id.news_show_back)
	public void back(View v)
	{
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_show);

		ViewUtils.inject(this);

		Intent intent = getIntent();
		String url = intent.getStringExtra(Constants.WEB_URL);
		String title = intent.getStringExtra(Constants.NEW_TITLE);

		mTvTitle.setText(title);

		mWvWeb.loadUrl(url);
		
		checkedItem = PreferenceUtils.getInt(getApplicationContext(), Constants.TEXT_SIZE);
		
		setTextSize(checkedItem);

	}

	// 设置选择字体大小的点击
	@OnClick(R.id.news_show_textsize)
	public void onSetTextSize(View v)
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("选择字体大小");

		CharSequence[] items = { "超大字体", "大字体", "正常字体", "小字体", "超小字体" };
		

		builder.setSingleChoiceItems(items, checkedItem, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				checkedItem=which;
				setTextSize(which);
				PreferenceUtils.setInt(getApplicationContext(), Constants.TEXT_SIZE, checkedItem);
			}
		});
		builder.show();

	}
	
	public void setTextSize(int size){
		
		WebSettings webSettings = mWvWeb.getSettings();
		
		TextSize tSize=null;
		switch (size)
		{
			case 0:
				tSize=TextSize.LARGEST;
				break;
			case 1:
				tSize=TextSize.LARGER;
				break;
			case 2:
				tSize=TextSize.NORMAL;
				break;
			case 3:
				tSize=TextSize.SMALLER;
				break;
			case 4:
				tSize=TextSize.SMALLEST;
				break;

			default:
				break;
		}
		
		webSettings.setTextSize(tSize);
	}

}
