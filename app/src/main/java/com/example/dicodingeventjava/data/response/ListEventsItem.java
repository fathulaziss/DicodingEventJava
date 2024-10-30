package com.example.dicodingeventjava.data.response;

import com.google.gson.annotations.SerializedName;

public class ListEventsItem{

	@SerializedName("summary")
	private String summary;

	@SerializedName("mediaCover")
	private String mediaCover;

	@SerializedName("registrants")
	private int registrants;

	@SerializedName("imageLogo")
	private String imageLogo;

	@SerializedName("link")
	private String link;

	@SerializedName("description")
	private String description;

	@SerializedName("ownerName")
	private String ownerName;

	@SerializedName("cityName")
	private String cityName;

	@SerializedName("quota")
	private int quota;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("beginTime")
	private String beginTime;

	@SerializedName("endTime")
	private String endTime;

	@SerializedName("category")
	private String category;

	public void setSummary(String summary){
		this.summary = summary;
	}

	public String getSummary(){
		return summary;
	}

	public void setMediaCover(String mediaCover){
		this.mediaCover = mediaCover;
	}

	public String getMediaCover(){
		return mediaCover;
	}

	public void setRegistrants(int registrants){
		this.registrants = registrants;
	}

	public int getRegistrants(){
		return registrants;
	}

	public void setImageLogo(String imageLogo){
		this.imageLogo = imageLogo;
	}

	public String getImageLogo(){
		return imageLogo;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setOwnerName(String ownerName){
		this.ownerName = ownerName;
	}

	public String getOwnerName(){
		return ownerName;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setQuota(int quota){
		this.quota = quota;
	}

	public int getQuota(){
		return quota;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setBeginTime(String beginTime){
		this.beginTime = beginTime;
	}

	public String getBeginTime(){
		return beginTime;
	}

	public void setEndTime(String endTime){
		this.endTime = endTime;
	}

	public String getEndTime(){
		return endTime;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	@Override
 	public String toString(){
		return 
			"ListEventsItem{" + 
			"summary = '" + summary + '\'' + 
			",mediaCover = '" + mediaCover + '\'' + 
			",registrants = '" + registrants + '\'' + 
			",imageLogo = '" + imageLogo + '\'' + 
			",link = '" + link + '\'' + 
			",description = '" + description + '\'' + 
			",ownerName = '" + ownerName + '\'' + 
			",cityName = '" + cityName + '\'' + 
			",quota = '" + quota + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",beginTime = '" + beginTime + '\'' + 
			",endTime = '" + endTime + '\'' + 
			",category = '" + category + '\'' + 
			"}";
		}
}