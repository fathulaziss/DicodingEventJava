package com.example.dicodingeventjava.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.data.server.repository.EventRepository;

import java.util.List;

public class DarkModeViewModel extends ViewModel {
    private final EventRepository eventRepository;
    private final MutableLiveData<Boolean> _isDarkModeResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isNotificationResult = new MutableLiveData<>();

    public DarkModeViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LiveData<Boolean> isDarkMode() {
        return _isDarkModeResult;
    }

    public void setDarkMode(Boolean isDarkMode) {
        _isDarkModeResult.setValue(isDarkMode);
    }

    public LiveData<Boolean> isNotification() {
        return _isNotificationResult;
    }

    public void setNotification(Boolean isNotificationActive) {
        _isNotificationResult.setValue(isNotificationActive);
    }

    public LiveData<Result<List<Event>>> fetchActiveEvent() {
        return eventRepository.fetchActiveEvent();
    }
}
