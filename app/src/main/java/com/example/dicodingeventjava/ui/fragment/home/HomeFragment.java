package com.example.dicodingeventjava.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.ui.adapter.BannerAdapter;
import com.example.dicodingeventjava.ui.adapter.EventAdapter;
import com.example.dicodingeventjava.databinding.FragmentHomeBinding;
import com.example.dicodingeventjava.ui.viewmodel.HomeViewModel;
import com.example.dicodingeventjava.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private EventAdapter eventAdapter;
    private BannerAdapter bannerAdapter;
    private List<Event> upcomingEvents = new ArrayList<>();
    private List<Event> finishedEvents = new ArrayList<>();

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
        bannerAdapter = new BannerAdapter(getContext(), upcomingEvents);

        LinearLayoutManager layoutManagerFinishedEvent = new LinearLayoutManager(getContext());
        binding.rvFinishedEvent.setLayoutManager(layoutManagerFinishedEvent);
        eventAdapter = new EventAdapter(getContext(), finishedEvents);

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        HomeViewModel viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        viewModel.fetchUpcomingEvent().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result instanceof Result.Loading) {
                    binding.pbUpcomingEvent.setVisibility(View.VISIBLE);
                } else if (result instanceof Result.Success) {
                    upcomingEvents = ((Result.Success<List<Event>>) result).getData();
                    setUpcomingEventData(upcomingEvents);
                } else if (result instanceof Result.Error) {
                    binding.pbUpcomingEvent.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Terjadi kesalahan: "+ ((Result.Error<?>) result).getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.fetchFinishedEvent().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result instanceof Result.Loading) {
                    binding.pbFinishedEvent.setVisibility(View.VISIBLE);
                } else if (result instanceof Result.Success) {
                    finishedEvents = ((Result.Success<List<Event>>) result).getData();
                    setFinishedEventData(finishedEvents);
                } else if (result instanceof Result.Error) {
                    binding.pbFinishedEvent.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Terjadi kesalahan: "+ ((Result.Error<?>) result).getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpcomingEventData(List<Event> upcomingEvents) {
        binding.pbUpcomingEvent.setVisibility(View.GONE);
        bannerAdapter = new BannerAdapter(getContext(), upcomingEvents);
        binding.rvUpcomingEvent.setAdapter(bannerAdapter);
    }

    private void setFinishedEventData(List<Event> finishedEvents) {
        binding.pbFinishedEvent.setVisibility(View.GONE);
        eventAdapter = new EventAdapter(getContext(), finishedEvents);
        binding.rvFinishedEvent.setAdapter(eventAdapter);
    }
}