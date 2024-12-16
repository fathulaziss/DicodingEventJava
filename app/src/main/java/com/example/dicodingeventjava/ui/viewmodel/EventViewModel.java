package com.example.dicodingeventjava.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.data.server.repository.EventRepository;

import java.util.List;

public class EventViewModel extends ViewModel {
    private final EventRepository eventRepository;

    public EventViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LiveData<Result<List<Event>>> fetchUpcomingEvent() {
        return eventRepository.fetchUpcomingEvent();
    }

    public LiveData<Result<List<Event>>> fetchFinishedEvent() {
        return eventRepository.fetchFinishedEvent();
    }

    public LiveData<Result<Event>> fetchEventDetail(int eventId) {
        return eventRepository.fetchEventDetail(eventId);
    }

    public LiveData<Result<List<Event>>> fetchFavoriteEvent() {
        return eventRepository.fetchFavoriteEvent();
    }

    public void searchFinishedEvent(String keyword) {
        eventRepository.searchFinishedEvent(keyword);
    }

    public void setFavoriteEvent(Event event) {
        eventRepository.setFavoriteEvent(event);
    }

    public void removeFavoriteEvent(Event event) {
        eventRepository.removeFavoriteEvent(event);
    }
}
