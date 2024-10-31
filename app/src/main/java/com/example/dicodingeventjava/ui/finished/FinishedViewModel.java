package com.example.dicodingeventjava.ui.finished;

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

public class FinishedViewModel extends ViewModel {
    private static final String TAG = "FinishedViewModel";

    private final MutableLiveData<List<ListEventsItem>> _listEvent = new MutableLiveData<>();
    public LiveData<List<ListEventsItem>> getListEvent() { return _listEvent; }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading() { return _isLoading; }

    public FinishedViewModel() {
        fetchFinishedEvent();
    }

    public final void fetchFinishedEvent() {
        _isLoading.setValue(true);
        Call<EventResponse> client = ApiConfig.getApiService().getEvent(0);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        _listEvent.setValue(response.body().getListEvents());
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG,"onFailure: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                _isLoading.setValue(false);
                Log.e(TAG,"onFailure: " + t.getMessage());
            }
        });
    }
}
