package com.example.dicodingeventjava.data.retrofit;

import com.example.dicodingeventjava.data.response.EventResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("events")
    Call<EventResponse> getEvent(@Query("active") int active);

    @GET("events")
    Call<EventResponse> searchEvent(@Query("active") int active, @Query("q") String q);
}