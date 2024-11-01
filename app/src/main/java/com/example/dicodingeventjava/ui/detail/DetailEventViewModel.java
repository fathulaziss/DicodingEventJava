package com.example.dicodingeventjava.ui.detail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dicodingeventjava.data.response.DetailEventResponse;
import com.example.dicodingeventjava.data.response.Event;
import com.example.dicodingeventjava.data.response.EventResponse;
import com.example.dicodingeventjava.data.retrofit.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventViewModel extends ViewModel {
    private static final String TAG = "DetailEventViewModel";

    private final MutableLiveData<Event> _detailEvent = new MutableLiveData<>();
    public LiveData<Event> getDetailEvent() { return _detailEvent; }

    private final MutableLiveData<Boolean> _isLoadingDetailEvent = new MutableLiveData<>();
    public LiveData<Boolean> isLoadingDetailEvent() { return _isLoadingDetailEvent; }


    public final void fetchDetailEvent(int eventId) {
        _isLoadingDetailEvent.setValue(true);
        Call<DetailEventResponse> client = ApiConfig.getApiService().getDetailEvent(eventId);
        client.enqueue(new Callback<DetailEventResponse>() {
            @Override
            public void onResponse(@NonNull Call<DetailEventResponse> call, @NonNull Response<DetailEventResponse> response) {
                _isLoadingDetailEvent.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        _detailEvent.setValue(response.body().getEvent());
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailEventResponse> call, @NonNull Throwable t) {
                _isLoadingDetailEvent.setValue(false);
                Log.e(TAG,"onFailure: " + t.getMessage());
            }
        });
    }
}
