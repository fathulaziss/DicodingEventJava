package com.example.dicodingeventjava.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DarkModeViewModel extends ViewModel {
    private final MutableLiveData<Boolean> _isDarkMode = new MutableLiveData<>();

    public LiveData<Boolean> isDarkMode() {
        return _isDarkMode;
    }

    public void setDarkMode(Boolean isDarkMode) {
        _isDarkMode.setValue(isDarkMode);
    }
}
