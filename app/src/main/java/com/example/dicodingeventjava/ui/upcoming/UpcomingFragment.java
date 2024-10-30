package com.example.dicodingeventjava.ui.upcoming;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dicodingeventjava.data.response.ListEventsItem;
import com.example.dicodingeventjava.databinding.FragmentUpcomingBinding;
import com.example.dicodingeventjava.ui.EventAdapter;

import java.util.List;

public class UpcomingFragment extends Fragment {

    private FragmentUpcomingBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvEvent.setLayoutManager(layoutManager);

        UpcomingViewModel upcomingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UpcomingViewModel.class);
        upcomingViewModel.getListEvent().observe(getViewLifecycleOwner(), this::setEventData);
        upcomingViewModel.isLoading().observe(getViewLifecycleOwner(), this::showLoading);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setEventData(List<ListEventsItem> upcomingEvents) {
        EventAdapter eventAdapter = new EventAdapter(getContext(), upcomingEvents);
        binding.rvEvent.setAdapter(eventAdapter);
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}