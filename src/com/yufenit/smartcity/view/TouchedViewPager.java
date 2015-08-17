package com.yufenit.smartcity.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.view
 * @创建时间 2015-8-17 下午6:42:43
 * @author Administrator
 * @描述 TODO
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: TODO
 * 
 */

public class TouchedViewPager extends ViewPager
{

	private static final String	TAG	= "TouchedViewPager";
	private int					mStartX;
	private int					mStartY;

	public TouchedViewPager(Context context) {
		super(context);
	}

	public TouchedViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		//
		int currentItem = getCurrentItem();

		if (currentItem == (getAdapter().getCount() - 1) || currentItem == 0)
		{
			requestDisallowInterceptTouchEvent(true);
			
			switch (ev.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					mStartX = (int) ev.getRawX();
					mStartY = (int) ev.getRawY();
					break;
				case MotionEvent.ACTION_UP:

					break;
				case MotionEvent.ACTION_MOVE:
					int endX = (int) ev.getRawX();
					int endY = (int) ev.getRawY();

					// 判断是下拉还是侧滑
					if ((Math.abs(mStartX - endX)) < (Math.abs(mStartY - endY)))
					{
//						System.out.println("下拉" );
						// 下拉
						break;
					}
					else
					{
						// 侧滑
						// 判断是左滑还是右滑
						if ((mStartX - endX) >= 0)
						{
//							System.out.println("手向左滑" );
							// 手向左滑
							// Log.d(TAG, "手向左滑");
							if (currentItem >= 0 && currentItem < getAdapter().getCount() - 1)
							{
								// 如果是第一个
								// Log.d(TAG, "手向左滑第一个");
								requestDisallowInterceptTouchEvent(true);
							}
							else if (currentItem == getAdapter().getCount() - 1)
							{
								// 如果不是第一个
								requestDisallowInterceptTouchEvent(false);
							}
							// requestDisallowInterceptTouchEvent(true);
						}
						else
						{
//							System.out.println("手向右滑" );
							// Log.d(TAG, "手向右滑");
							// 手向右滑
							if (currentItem == 0)
							{
								// Log.d(TAG, "右滑最后一个");
								requestDisallowInterceptTouchEvent(false);
							}
							else
							{
								// 如果不是最后一个
								requestDisallowInterceptTouchEvent(true);
							}

						}
					}
					mStartX=(int) ev.getRawX();
					mStartY=(int) ev.getRawY();
					break;

				default:
					break;
			}

		}
		else
		{
			requestDisallowInterceptTouchEvent(true);
		}

		return super.dispatchTouchEvent(ev);
	}

}
