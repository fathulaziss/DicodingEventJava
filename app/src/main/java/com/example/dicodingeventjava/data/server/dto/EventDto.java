package com.example.dicodingeventjava.data.server.dto;

import androidx.annotation.NonNull;

import com.example.dicodingeventjava.data.local.entity.Event;
import com.google.gson.annotations.SerializedName;

public class EventDto {

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMediaCover() {
        return mediaCover;
    }

    public void setMediaCover(String mediaCover) {
        this.mediaCover = mediaCover;
    }

    public int getRegistrants() {
        return registrants;
    }

    public void setRegistrants(int registrants) {
        this.registrants = registrants;
    }

    public String getImageLogo() {
        return imageLogo;
    }

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NonNull
    @Override
    public String toString() {
        return "EventDto{" +
                "summary='" + summary + '\'' +
                ", mediaCover='" + mediaCover + '\'' +
                ", registrants=" + registrants +
                ", imageLogo='" + imageLogo + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", quota=" + quota +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
