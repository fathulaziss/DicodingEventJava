package com.example.dicodingeventjava.ui.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dicodingeventjava.data.server.repository.EventRepository;
import com.example.dicodingeventjava.di.Injection;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final EventRepository eventRepository;

    private ViewModelFactory(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public static ViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EventViewModel.class)) {
            return (T) new EventViewModel(eventRepository);
        } else if (modelClass.isAssignableFrom(DarkModeViewModel.class)) {
            return (T) new DarkModeViewModel(eventRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
