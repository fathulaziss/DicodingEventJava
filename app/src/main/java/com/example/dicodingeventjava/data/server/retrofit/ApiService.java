package com.example.dicodingeventjava.data.server.retrofit;

import com.example.dicodingeventjava.data.server.response.EventDetailResponse;
import com.example.dicodingeventjava.data.server.response.EventResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("events")
    Call<EventResponse> getEvent(@Query("active") int active,
                                 @Query("limit") int limit);

    @GET("events/{id}")
    Call<EventDetailResponse> getDetailEvent(@Path("id") int id);

    @GET("events")
    Call<EventResponse> searchEvent(@Query("active") int active, @Query("q") String q);
}
