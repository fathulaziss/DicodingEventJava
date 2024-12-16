package com.example.dicodingeventjava.data.server.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.dicodingeventjava.data.local.dao.EventDao;
import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.data.server.dto.EventDto;
import com.example.dicodingeventjava.data.server.response.EventDetailResponse;
import com.example.dicodingeventjava.data.server.response.EventResponse;
import com.example.dicodingeventjava.data.server.retrofit.ApiService;
import com.example.dicodingeventjava.utils.AppExecutors;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRepository {
    private volatile static EventRepository INSTANCE = null;

    private final ApiService apiService;
    private final EventDao eventDao;
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Result<List<Event>>> listUpcomingEvent = new MediatorLiveData<>();
    private final MediatorLiveData<Result<List<Event>>> listFinishedEvent = new MediatorLiveData<>();
    private final MediatorLiveData<Result<List<Event>>> listFavoriteEvent = new MediatorLiveData<>();
    private final MediatorLiveData<Result<Event>> eventDetail = new MediatorLiveData<>();

    private EventRepository(@NonNull ApiService apiService, EventDao eventDao, AppExecutors appExecutors) {
        this.apiService = apiService;
        this.eventDao = eventDao;
        this.appExecutors = appExecutors;
    }

    public static EventRepository getInstance(ApiService apiService, EventDao eventDao, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (EventRepository.class) {
                INSTANCE = new EventRepository(apiService, eventDao, appExecutors);
            }
        }
        return INSTANCE;
    }

    public LiveData<Result<List<Event>>> fetchUpcomingEvent() {
        listUpcomingEvent.setValue(new Result.Loading<>());

        Call<EventResponse> client = apiService.getEvent(1);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Event> events = response.body().getListEvents()
                                .stream().map(eventDto -> new Event(
                                        eventDto.getSummary(),
                                        eventDto.getMediaCover(),
                                        eventDto.getRegistrants(),
                                        eventDto.getImageLogo(),
                                        eventDto.getLink(),
                                        eventDto.getDescription(),
                                        eventDto.getOwnerName(),
                                        eventDto.getCityName(),
                                        eventDto.getQuota(),
                                        eventDto.getName(),
                                        eventDto.getId(),
                                        eventDto.getBeginTime(),
                                        eventDto.getEndTime(),
                                        eventDto.getCategory(),
                                        false
                                ))
                                .collect(Collectors.toList());
                        listUpcomingEvent.setValue(new Result.Success<>(events.subList(0, Math.min(events.size(), 5))));
                    }else {
                        eventDetail.setValue(new Result.Error<>(response.message()));
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

    public LiveData<Result<List<Event>>> fetchFinishedEvent() {
        listFinishedEvent.setValue(new Result.Loading<>());

        Call<EventResponse> client = apiService.getEvent(0);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Event> events = response.body().getListEvents()
                                .stream().map(eventDto -> new Event(
                                        eventDto.getSummary(),
                                        eventDto.getMediaCover(),
                                        eventDto.getRegistrants(),
                                        eventDto.getImageLogo(),
                                        eventDto.getLink(),
                                        eventDto.getDescription(),
                                        eventDto.getOwnerName(),
                                        eventDto.getCityName(),
                                        eventDto.getQuota(),
                                        eventDto.getName(),
                                        eventDto.getId(),
                                        eventDto.getBeginTime(),
                                        eventDto.getEndTime(),
                                        eventDto.getCategory(),
                                        false
                                ))
                                .collect(Collectors.toList());
                        listFinishedEvent.setValue(new Result.Success<>(events.subList(0, Math.min(events.size(), 5))));
                    }
                } else {
                    eventDetail.setValue(new Result.Error<>(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                listFinishedEvent.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        return listFinishedEvent;
    }

    public void searchFinishedEvent(String keyword) {
        listFinishedEvent.setValue(new Result.Loading<>());

        Call<EventResponse> client = apiService.searchEvent(0, keyword);
        client.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Event> events = response.body().getListEvents()
                                .stream().map(eventDto -> new Event(
                                        eventDto.getSummary(),
                                        eventDto.getMediaCover(),
                                        eventDto.getRegistrants(),
                                        eventDto.getImageLogo(),
                                        eventDto.getLink(),
                                        eventDto.getDescription(),
                                        eventDto.getOwnerName(),
                                        eventDto.getCityName(),
                                        eventDto.getQuota(),
                                        eventDto.getName(),
                                        eventDto.getId(),
                                        eventDto.getBeginTime(),
                                        eventDto.getEndTime(),
                                        eventDto.getCategory(),
                                        false
                                ))
                                .collect(Collectors.toList());
                        listFinishedEvent.setValue(new Result.Success<>(events.subList(0, Math.min(events.size(), 5))));
                    }
                } else {
                    eventDetail.setValue(new Result.Error<>(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
                listFinishedEvent.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });
    }

    public LiveData<Result<Event>> fetchEventDetail(int eventId) {
        eventDetail.setValue(new Result.Loading<>());

        Call<EventDetailResponse> client = apiService.getDetailEvent(eventId);
        client.enqueue(new Callback<EventDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventDetailResponse> call, @NonNull Response<EventDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        EventDto eventDto = response.body().getEvent();
                        appExecutors.diskIO().execute(() -> {
                            Event eventEntity = eventDao.getEventById(eventDto.getId());
                            if (eventEntity != null) {
                                Event event = new Event(
                                        eventEntity.getSummary(),
                                        eventEntity.getMediaCover(),
                                        eventEntity.getRegistrants(),
                                        eventEntity.getImageLogo(),
                                        eventEntity.getLink(),
                                        eventEntity.getDescription(),
                                        eventEntity.getOwnerName(),
                                        eventEntity.getCityName(),
                                        eventEntity.getQuota(),
                                        eventEntity.getName(),
                                        eventEntity.getEventId(),
                                        eventEntity.getBeginTime(),
                                        eventEntity.getEndTime(),
                                        eventEntity.getCategory(),
                                        eventEntity.getFavorite()
                                );
                                eventDetail.postValue(new Result.Success<>(event));
                            } else {
                                Event event = new Event(
                                        eventDto.getSummary(),
                                        eventDto.getMediaCover(),
                                        eventDto.getRegistrants(),
                                        eventDto.getImageLogo(),
                                        eventDto.getLink(),
                                        eventDto.getDescription(),
                                        eventDto.getOwnerName(),
                                        eventDto.getCityName(),
                                        eventDto.getQuota(),
                                        eventDto.getName(),
                                        eventDto.getId(),
                                        eventDto.getBeginTime(),
                                        eventDto.getEndTime(),
                                        eventDto.getCategory(),
                                        false
                                );
                                eventDetail.postValue(new Result.Success<>(event));
                            }
                        });
                    }
                } else {
                    eventDetail.setValue(new Result.Error<>(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventDetailResponse> call, @NonNull Throwable t) {
                eventDetail.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        return eventDetail;
    }

    public LiveData<Result<List<Event>>> fetchFavoriteEvent() {
        listFavoriteEvent.setValue(new Result.Loading<>());

        appExecutors.diskIO().execute(() -> {
            List<Event> events = eventDao.getFavoriteEvent();
            listFavoriteEvent.postValue(new Result.Success<>(events));
        });
        return listFavoriteEvent;
    }

    public void setFavoriteEvent(Event eventData) {
        appExecutors.diskIO().execute(() -> {
            Event eventEntity = eventDao.getEventById(eventData.getEventId());
            Event event = new Event(
                    eventData.getSummary(),
                    eventData.getMediaCover(),
                    eventData.getRegistrants(),
                    eventData.getImageLogo(),
                    eventData.getLink(),
                    eventData.getDescription(),
                    eventData.getOwnerName(),
                    eventData.getCityName(),
                    eventData.getQuota(),
                    eventData.getName(),
                    eventData.getEventId(),
                    eventData.getBeginTime(),
                    eventData.getEndTime(),
                    eventData.getCategory(),
                    true
            );
            if (eventEntity != null) {
                if (eventEntity.getFavorite()) {
                    eventDao.updateFavoriteEvent(0, eventData.getEventId());
                } else {
                    eventDao.updateFavoriteEvent(1, eventData.getEventId());
                }
            } else {
                eventDao.insert(event);
            }
            eventDetail.postValue(new Result.Success<>(event));
        });
    }

    public void removeFavoriteEvent(Event eventData) {
        appExecutors.diskIO().execute(() -> {
            Event event = new Event(
                    eventData.getSummary(),
                    eventData.getMediaCover(),
                    eventData.getRegistrants(),
                    eventData.getImageLogo(),
                    eventData.getLink(),
                    eventData.getDescription(),
                    eventData.getOwnerName(),
                    eventData.getCityName(),
                    eventData.getQuota(),
                    eventData.getName(),
                    eventData.getEventId(),
                    eventData.getBeginTime(),
                    eventData.getEndTime(),
                    eventData.getCategory(),
                    false
            );
            eventDao.updateFavoriteEvent(0, eventData.getEventId());
            eventDetail.postValue(new Result.Success<>(event));
        });
    }
}
