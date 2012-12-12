package com.lzhih.DatabaseOperate;

import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.lzhih.jsonData.Tag;

public class BookData
{
	@DatabaseField(generatedId = true)
	private int id ;
	
	private String alt;
	
	private String rating;
	@DatabaseField(defaultValue = "AAA")
	private String author;
	private String name;
	public List<Tag> tags;
	@DatabaseField
	private String summary;
	@DatabaseField
	private String title;
	@DatabaseField
	private String price;
	@DatabaseField
	private String publisher;
	@DatabaseField
	private String pubdate;
	private String binding;
	@DatabaseField
	private String translator;
	@DatabaseField
	private String pages;
	@DatabaseField
	private String userID;
//	@DatabaseField
	private String images;
//	@DatabaseField
	private String url;
	
	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public BookData()
	{
		// TODO Auto-generated constructor stub
	}
	public BookData(String author,String summary,String title,String price,String publisher,String pubdate,String translator,String pages,String userID)
	{
		this.author = author;
		this.summary = summary;
		this.title = title;
		this.price = price;
		this.publisher = publisher;
		this.pubdate = pubdate;
		this.translator = translator;
		this.pages = pages;
		this.userID = userID;
	}
	public String getImages()
	{
		return images;
	}
	public void setImages(String images)
	{
		this.images = images;
	}
	public String getPublisher()
	{
		return publisher;
	}
	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}
	public String getPubdate()
	{
		return pubdate;
	}
	public void setPubdate(String pubdate)
	{
		this.pubdate = pubdate;
	}
	public String getBinding()
	{
		return binding;
	}
	public void setBinding(String binding)
	{
		this.binding = binding;
	}
	public String getTranslator()
	{
		return translator;
	}
	public void setTranslator(String translator)
	{
		this.translator = translator;
	}
	public String getPages()
	{
		return pages;
	}
	public void setPages(String pages)
	{
		this.pages = pages;
	}
	public String getPrice()
	{
		return price;
	}
	public void setPrice(String price)
	{
		this.price = price;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getSummary()
	{
		return summary;
	}
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	public List<Tag> getTags()
	{
		return tags;
	}
	public void setTags(List<Tag> tags)
	{
		this.tags = tags;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getAlt()
	{
		return alt;
	}
	public void setAlt(String alt)
	{
		this.alt = alt;
	}
	public String getRating()
	{
		return rating;
	}
	public void setRating(String rating)
	{
		this.rating = rating;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
}
