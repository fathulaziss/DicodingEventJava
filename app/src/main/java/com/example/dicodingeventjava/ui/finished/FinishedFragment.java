package com.example.dicodingeventjava.ui.finished;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dicodingeventjava.adapter.EventAdapter;
import com.example.dicodingeventjava.data.response.ListEventsItem;
import com.example.dicodingeventjava.databinding.FragmentFinishedBinding;

import java.util.List;

public class FinishedFragment extends Fragment {

    private FragmentFinishedBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFinishedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvEvent.setLayoutManager(layoutManager);

        FinishedViewModel finishedViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FinishedViewModel.class);
        finishedViewModel.getListEvent().observe(getViewLifecycleOwner(), this::setEventData);
        finishedViewModel.isLoading().observe(getViewLifecycleOwner(), this::showLoading);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setEventData(List<ListEventsItem> finishedEvents) {
        EventAdapter eventAdapter = new EventAdapter(getContext(), finishedEvents);
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