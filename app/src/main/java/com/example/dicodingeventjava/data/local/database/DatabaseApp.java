package com.example.dicodingeventjava.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dicodingeventjava.data.local.dao.EventDao;
import com.example.dicodingeventjava.data.local.entity.Event;

@Database(entities = { Event.class }, version = 1, exportSchema = false)
public abstract class DatabaseApp extends RoomDatabase {
    public abstract EventDao eventDao();

    private static DatabaseApp INSTANCE;

    public static DatabaseApp getDatabaseApp(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseApp.class,
                    "database_event").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
