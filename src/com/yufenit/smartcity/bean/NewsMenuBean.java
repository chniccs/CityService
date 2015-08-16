package com.yufenit.smartcity.bean;

import java.util.List;

/**
 * @项目名 SmartCity
 * @包名 com.yufenit.smartcity.bean
 * @创建时间 2015-8-16 下午6:40:00
 * @author Administrator
 * @描述 TODO
 * 
 * @SVN版本号 $Rev$
 * @修改人 $Author$
 * @修改时间: $Date$
 * @修改的内容: TODO
 * 
 */

public class NewsMenuBean
{
	public NewsData	data;
	public int		retcode;

	public class NewsData
	{
		public String						countcommenturl;
		public String						more;
		public List<NewsMenuNewsBean>		news;
		public String						title;
		public List<NewsMenuTopicBean>		topic;
		public List<NewsMenuTopnewsBean>	topnews;

		public class NewsMenuNewsBean
		{

			public boolean	comment;
			public String	commentlist;
			public String	commenturl;
			public Long		id;
			public String	listimage;
			public String	pubdate;
			public String	title;
			public String	type;
			public String	url;
		}

		public class NewsMenuTopicBean
		{
			public Long		description;
			public int		id;
			public String	listimage;
			public int		sort;
			public String	title;
			public String	url;
		}

		public class NewsMenuTopnewsBean
		{
			public boolean	comment;
			public String	commentlist;
			public String	commenturl;
			public int		id;
			public String	pubdate;
			public String	title;
			public String	topimage;
			public String	type;
			public String	url;
		}
	}

}
