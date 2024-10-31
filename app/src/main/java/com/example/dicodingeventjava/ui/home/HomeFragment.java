package com.example.dicodingeventjava.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dicodingeventjava.adapter.BannerAdapter;
import com.example.dicodingeventjava.adapter.EventAdapter;
import com.example.dicodingeventjava.data.response.ListEventsItem;
import com.example.dicodingeventjava.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManagerUpcomingEvent = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvUpcomingEvent.setLayoutManager(layoutManagerUpcomingEvent);
        LinearLayoutManager layoutManagerFinishedEvent = new LinearLayoutManager(getContext());
        binding.rvFinishedEvent.setLayoutManager(layoutManagerFinishedEvent);

        HomeViewModel homeViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        homeViewModel.getListUpcomingEvent().observe(getViewLifecycleOwner(), this::setUpcomingEventData);
        homeViewModel.isLoadingFinishedEvent().observe(getViewLifecycleOwner(), this::showUpcomingEventLoading);
        homeViewModel.getListFinishedEvent().observe(getViewLifecycleOwner(), this::setFinishedEventData);
        homeViewModel.isLoadingFinishedEvent().observe(getViewLifecycleOwner(), this::showFinishedEventLoading);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpcomingEventData(List<ListEventsItem> upcomingEventData) {
        BannerAdapter bannerAdapter = new BannerAdapter(getContext(), upcomingEventData);
        binding.rvUpcomingEvent.setAdapter(bannerAdapter);
    }

    private void showUpcomingEventLoading(Boolean isLoading) {
        if (isLoading) {
            binding.pbUpcomingEvent.setVisibility(View.VISIBLE);
        } else {
            binding.pbUpcomingEvent.setVisibility(View.GONE);
        }
    }

    private void setFinishedEventData(List<ListEventsItem> finishedEventData) {
        EventAdapter eventAdapter = new EventAdapter(getContext(), finishedEventData);
        binding.rvFinishedEvent.setAdapter(eventAdapter);
    }

    private void showFinishedEventLoading(Boolean isLoading) {
        if (isLoading) {
            binding.pbFinishedEvent.setVisibility(View.VISIBLE);
        } else {
            binding.pbFinishedEvent.setVisibility(View.GONE);
        }
    }
}