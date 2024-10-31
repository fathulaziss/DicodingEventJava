package com.example.dicodingeventjava.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dicodingeventjava.data.response.EventResponse;
import com.example.dicodingeventjava.data.response.ListEventsItem;
import com.example.dicodingeventjava.data.retrofit.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";

    private final MutableLiveData<List<ListEventsItem>> _listUpcomingEvent = new MutableLiveData<>();
    public LiveData<List<ListEventsItem>> getListUpcomingEvent() { return _listUpcomingEvent; }

    public final MutableLiveData<List<ListEventsItem>> _listFinishedEvent = new MutableLiveData<>();
    public LiveData<List<ListEventsItem>> getListFinishedEvent() { return _listFinishedEvent; }

    public final MutableLiveData<Boolean> _isLoadingUpcomingEvent = new MutableLiveData<>();
    public LiveData<Boolean> isLoadingUpcomingEvent() { return _isLoadingUpcomingEvent; }

    public final MutableLiveData<Boolean> _isLoadingFinishedEvent = new MutableLiveData<>();
    public LiveData<Boolean> isLoadingFinishedEvent() { return _isLoadingFinishedEvent; }

    public HomeViewModel() {
        fetchUpcomingEvent();
        fetchFinishedEvent();
    }

    public final void fetchUpcomingEvent() {
        _isLoadingUpcomingEvent.setValue(true);
        Call<EventResponse> client = ApiConfig.getApiService().getEvent(1);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                _isLoadingUpcomingEvent.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        _listUpcomingEvent.setValue(response.body().getListEvents().subList(0, Math.min(response.body().getListEvents().size(), 5)));
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG,"onFailure: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                _isLoadingUpcomingEvent.setValue(false);
                Log.e(TAG,"onFailure: " + t.getMessage());
            }
        });
    }

    public final void fetchFinishedEvent() {
        _isLoadingFinishedEvent.setValue(true);
        Call<EventResponse> client = ApiConfig.getApiService().getEvent(0);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                _isLoadingFinishedEvent.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        _listFinishedEvent.setValue(response.body().getListEvents().subList(0, Math.min(response.body().getListEvents().size(), 5)));
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG,"onFailure: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                _isLoadingFinishedEvent.setValue(false);
                Log.e(TAG,"onFailure: " + t.getMessage());
            }
        });
    }
}