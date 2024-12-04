package com.example.dicodingeventjava.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.data.server.dto.EventDto;
import com.example.dicodingeventjava.data.server.repository.EventRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final EventRepository eventRepository;

    public HomeViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LiveData<Result<List<EventDto>>> fetchUpcomingEvent() {
        return eventRepository.fetchUpcomingEvent();
    }

    public LiveData<Result<List<EventDto>>> fetchFinishedEvent() {
        return eventRepository.fetchFinishedEvent();
    }
}
