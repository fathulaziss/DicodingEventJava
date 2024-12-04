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

import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.ui.adapter.BannerAdapter;
import com.example.dicodingeventjava.ui.adapter.EventAdapter;
import com.example.dicodingeventjava.data.server.dto.EventDto;
import com.example.dicodingeventjava.databinding.FragmentHomeBinding;
import com.example.dicodingeventjava.ui.viewmodel.HomeViewModel;
import com.example.dicodingeventjava.ui.viewmodel.ViewModelFactory;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private EventAdapter eventAdapter;
    private BannerAdapter bannerAdapter;

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
        bannerAdapter = new BannerAdapter(getContext(), null);

        LinearLayoutManager layoutManagerFinishedEvent = new LinearLayoutManager(getContext());
        binding.rvFinishedEvent.setLayoutManager(layoutManagerFinishedEvent);
        eventAdapter = new EventAdapter(getContext(), null);

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        HomeViewModel viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        viewModel.fetchUpcomingEvent().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result instanceof Result.Loading) {
                    showUpcomingEventLoading(true);
                } else if (result instanceof Result.Success) {
                    showUpcomingEventLoading(false);
                    List<EventDto> events = ((Result.Success<List<EventDto>>) result).getData();
                    setUpcomingEventData(events);
                } else if (result instanceof Result.Error) {
                    showUpcomingEventLoading(false);
                    Toast.makeText(getContext(), "Terjadi kesalahan"+ ((Result.Error<?>) result).getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.fetchFinishedEvent().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result instanceof Result.Loading) {
                    showFinishedEventLoading(true);
                } else if (result instanceof Result.Success) {
                    showFinishedEventLoading(false);
                    List<EventDto> events = ((Result.Success<List<EventDto>>) result).getData();
                    setFinishedEventData(events);
                } else if (result instanceof Result.Error) {
                    showFinishedEventLoading(false);
                    Toast.makeText(getContext(), "Terjadi kesalahan"+ ((Result.Error<?>) result).getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpcomingEventData(List<EventDto> upcomingEventData) {
        bannerAdapter = new BannerAdapter(getContext(), upcomingEventData);
        binding.rvUpcomingEvent.setAdapter(bannerAdapter);
    }

    private void showUpcomingEventLoading(Boolean isLoading) {
        if (isLoading) {
            binding.pbUpcomingEvent.setVisibility(View.VISIBLE);
        } else {
            binding.pbUpcomingEvent.setVisibility(View.GONE);
        }
    }

    private void setFinishedEventData(List<EventDto> finishedEventData) {
        eventAdapter = new EventAdapter(getContext(), finishedEventData);
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