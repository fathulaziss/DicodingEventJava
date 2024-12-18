package com.example.dicodingeventjava.di;

import android.content.Context;

import androidx.work.WorkManager;

import com.example.dicodingeventjava.data.local.dao.EventDao;
import com.example.dicodingeventjava.data.local.database.DatabaseApp;
import com.example.dicodingeventjava.data.server.repository.EventRepository;
import com.example.dicodingeventjava.data.server.retrofit.ApiConfig;
import com.example.dicodingeventjava.data.server.retrofit.ApiService;
import com.example.dicodingeventjava.utils.AppExecutors;

public class Injection {
    public static EventRepository provideRepository(Context context) {
        ApiService apiService = ApiConfig.getApiService();
        DatabaseApp databaseApp = DatabaseApp.getDatabaseApp(context);
        EventDao dao = databaseApp.eventDao();
        AppExecutors appExecutors = new AppExecutors();
        return EventRepository.getInstance(apiService, dao, appExecutors);
    }
}
