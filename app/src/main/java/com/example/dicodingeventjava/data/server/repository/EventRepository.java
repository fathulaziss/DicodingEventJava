package com.example.dicodingeventjava.data.server.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.data.server.dto.EventDto;
import com.example.dicodingeventjava.data.server.response.EventResponse;
import com.example.dicodingeventjava.data.server.retrofit.ApiService;
import com.example.dicodingeventjava.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRepository {
    private volatile static EventRepository INSTANCE = null;

    private final ApiService apiService;
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Result<List<EventDto>>> listUpcomingEvent = new MediatorLiveData<>();
    private final MediatorLiveData<Result<List<EventDto>>> listFinishedEvent = new MediatorLiveData<>();

    private EventRepository(@NonNull ApiService apiService, AppExecutors appExecutors) {
        this.apiService = apiService;
        this.appExecutors = appExecutors;
    }

    public static EventRepository getInstance(ApiService apiService, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (EventRepository.class) {
                INSTANCE = new EventRepository(apiService, appExecutors);
            }
        }
        return INSTANCE;
    }

    public LiveData<Result<List<EventDto>>> fetchUpcomingEvent() {
        listUpcomingEvent.setValue(new Result.Loading<>());

        Call<EventResponse> client = apiService.getEvent(1);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<EventDto> events = response.body().getListEvents();
                        listUpcomingEvent.setValue(new Result.Success<>(events.subList(0, Math.min(events.size(), 5))));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                listUpcomingEvent.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        return listUpcomingEvent;
    }

    public LiveData<Result<List<EventDto>>> fetchFinishedEvent() {
        listFinishedEvent.setValue(new Result.Loading<>());

        Call<EventResponse> client = apiService.getEvent(0);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<EventDto> events = response.body().getListEvents();
                        listFinishedEvent.setValue(new Result.Success<>(events.subList(0, Math.min(events.size(), 5))));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                listFinishedEvent.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        return listFinishedEvent;
    }
}
