package com.yufenit.smartcity.bean;

import java.util.List;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.bean
 * @创建时间 2015-8-15 上午11:23:48
 * @author Administrator
 * @描述 新闻中的JAVABEAN
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容:
 * 
 */

public class NewsCenterBean
{
	public List<NewsCenterMenuBean>	data;
	public List<Long>				extend;
	public int						retcode;
	
	public class NewsCenterMenuBean{
		
		public List<NewListBean> children;
		public int id;
		public String title;
		public int type;
		
		public String url;
		public String url1;
		
		public String dayurl;
		public String excurl;
	
		public String weekurl;
		
		public class NewListBean{
			public int id;
			public String title;
			public int type;
			public String url;
		}
	}
}
