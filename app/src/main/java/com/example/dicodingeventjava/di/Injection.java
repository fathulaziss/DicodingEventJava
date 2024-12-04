package com.example.dicodingeventjava.di;

import android.content.Context;

import com.example.dicodingeventjava.data.server.repository.EventRepository;
import com.example.dicodingeventjava.data.server.retrofit.ApiConfig;
import com.example.dicodingeventjava.data.server.retrofit.ApiService;
import com.example.dicodingeventjava.utils.AppExecutors;

public class Injection {
    public static EventRepository provideRepository(Context context) {
        ApiService apiService = ApiConfig.getApiService();
        AppExecutors appExecutors = new AppExecutors();
        return EventRepository.getInstance(apiService, appExecutors);
    }
}
