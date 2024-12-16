package com.example.dicodingeventjava.data.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.dicodingeventjava.data.local.entity.Event;

import java.util.List;

@Dao
public abstract class EventDao implements BaseDao<Event> {

    @Query("SELECT * FROM Event where event_id = :eventId")
    public abstract Event getEventById(int eventId);

    @Query("SELECT * FROM Event where is_favorite = 1")
    public abstract List<Event> getFavoriteEvent();

    @Query("UPDATE Event set is_favorite = :isFavorite where event_id = :eventId")
    public abstract void updateFavoriteEvent(int isFavorite, int eventId);

    @Query("DELETE FROM Event")
    public abstract void deleteAll();
}
