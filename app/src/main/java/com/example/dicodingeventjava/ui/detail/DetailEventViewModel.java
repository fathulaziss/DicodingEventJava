package com.example.dicodingeventjava.ui.detail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dicodingeventjava.data.server.dto.EventDto;
import com.example.dicodingeventjava.data.server.response.EventDetailResponse;
import com.example.dicodingeventjava.data.server.retrofit.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventViewModel extends ViewModel {
    private static final String TAG = "DetailEventViewModel";

    private final MutableLiveData<EventDto> _detailEvent = new MutableLiveData<>();
    public LiveData<EventDto> getDetailEvent() { return _detailEvent; }

    private final MutableLiveData<Boolean> _isLoadingDetailEvent = new MutableLiveData<>();
    public LiveData<Boolean> isLoadingDetailEvent() { return _isLoadingDetailEvent; }


    public final void fetchDetailEvent(int eventId) {
        _isLoadingDetailEvent.setValue(true);
        Call<EventDetailResponse> client = ApiConfig.getApiService().getDetailEvent(eventId);
        client.enqueue(new Callback<EventDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventDetailResponse> call, @NonNull Response<EventDetailResponse> response) {
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
            public void onFailure(@NonNull Call<EventDetailResponse> call, @NonNull Throwable t) {
                _isLoadingDetailEvent.setValue(false);
                Log.e(TAG,"onFailure: " + t.getMessage());
            }
        });
    }
}
