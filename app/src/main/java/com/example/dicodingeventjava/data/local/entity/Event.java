package com.example.dicodingeventjava.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Event", indices = {@Index("id")})
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "summary")
    private String summary;

    @ColumnInfo(name = "media_cover")
    private String mediaCover;

    @ColumnInfo(name = "registrants")
    private int registrants;

    @ColumnInfo(name = "image_logo")
    private String imageLogo;

    @ColumnInfo(name = "link")
    private String link;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "owner_name")
    private String ownerName;

    @ColumnInfo(name = "city_name")
    private String cityName;

    @ColumnInfo(name = "quota")
    private int quota;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "event_id")
    private int eventId;

    @ColumnInfo(name = "begin_time")
    private String beginTime;

    @ColumnInfo(name = "end_time")
    private String endTime;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "is_favorite")
    private Boolean isFavorite;

    public Event(String summary, String mediaCover, int registrants, String imageLogo, String link, String description, String ownerName, String cityName, int quota, String name, int eventId, String beginTime, String endTime, String category, Boolean isFavorite) {
        this.summary = summary;
        this.mediaCover = mediaCover;
        this.registrants = registrants;
        this.imageLogo = imageLogo;
        this.link = link;
        this.description = description;
        this.ownerName = ownerName;
        this.cityName = cityName;
        this.quota = quota;
        this.name = name;
        this.eventId = eventId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.category = category;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        eventId = eventId;
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

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
