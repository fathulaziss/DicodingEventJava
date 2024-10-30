package com.example.dicodingeventjava.ui.upcoming;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.data.response.ListEventsItem;
import com.example.dicodingeventjava.databinding.FragmentFinishedBinding;
import com.example.dicodingeventjava.databinding.FragmentUpcomingBinding;
import com.example.dicodingeventjava.ui.EventAdapter;

import java.util.ArrayList;
import java.util.List;

public class UpcomingFragment extends Fragment {

    private FragmentUpcomingBinding binding;
    private List<ListEventsItem> eventList;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        UpcomingViewModel upcomingViewModel =
                new ViewModelProvider(this).get(UpcomingViewModel.class);
        upcomingViewModel.getListEvent().observe(getViewLifecycleOwner(), this::setReviewData);
        upcomingViewModel.isLoading().observe(getViewLifecycleOwner(), this::showLoading);

        binding = FragmentUpcomingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setReviewData(List<ListEventsItem> upcomingEvents) {
        eventList.addAll(upcomingEvents);
        EventAdapter eventAdapter = new EventAdapter(getContext(), eventList);
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