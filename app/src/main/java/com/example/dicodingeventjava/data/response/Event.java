package com.example.dicodingeventjava.data.response;

public class Event{
	private String summary;
	private String mediaCover;
	private int registrants;
	private String imageLogo;
	private String link;
	private String description;
	private String ownerName;
	private String cityName;
	private int quota;
	private String name;
	private int id;
	private String beginTime;
	private String endTime;
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
			"Event{" + 
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
