package com.example.dicodingeventjava.data.local.dao;

import androidx.room.Query;

import com.example.dicodingeventjava.data.local.entity.Event;

public abstract class EventDao implements BaseDao<Event> {

    @Query("SELECT * FROM Event where event_id = :eventId")
    public abstract Event getEventById(int eventId);
}
