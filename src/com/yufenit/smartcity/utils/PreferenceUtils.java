package com.yufenit.smartcity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.utils
 * @创建时间 2015-8-13 下午6:27:26
 * @author chniccs
 * @描述 获得本地化存储的信息的工具类
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: TODO
 *
 */
public class PreferenceUtils {
	
	private static final String SP_NAME="config";
	
	private static SharedPreferences mSp;
	
	public static void getSp(Context context){
		
		if(mSp==null){
			mSp=context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		
	}
	
	public static boolean getBoolean(Context context,String key){
		
		 return getBoolean(context,key,true);
	}
	public static boolean getBoolean(Context context,String key,boolean defValue){
		
		getSp(context);
		
		return mSp.getBoolean(key, defValue);
	}
	
	public static void setBoolean(Context context,String key,boolean value){

		getSp(context);
		
		Editor edit = mSp.edit();
		
		edit.putBoolean(key, value);
		
		edit.commit();
		
	}
	public static String getString(Context context,String key){
		
		return getString(context,key,null);
	}
	public static String getString(Context context,String key,String defValue){
		
		getSp(context);
		
		return mSp.getString(key, defValue);
	}
	
	public static void setString(Context context,String key,String value){
		
		getSp(context);
		
		Editor edit = mSp.edit();
		
		edit.putString(key, value);
		
		edit.commit();
		
	}
	public static int getInt(Context context,String key){
		
		return getInt(context,key,2);
	}
	public static int getInt(Context context,String key,int defValue){
		
		getSp(context);
		
		return mSp.getInt(key, defValue);
	}
	
	public static void setInt(Context context,String key,int value){
		
		getSp(context);
		
		Editor edit = mSp.edit();
		
		edit.putInt(key, value);
		
		edit.commit();
		
	}
	
}
