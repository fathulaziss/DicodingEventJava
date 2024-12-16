package com.example.dicodingeventjava.ui.fragment.upcoming;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.databinding.FragmentUpcomingBinding;
import com.example.dicodingeventjava.ui.adapter.EventAdapter;
import com.example.dicodingeventjava.ui.viewmodel.EventViewModel;
import com.example.dicodingeventjava.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class UpcomingFragment extends Fragment {

    private FragmentUpcomingBinding binding;
    private EventAdapter eventAdapter;
    private List<Event> events = new ArrayList<>();

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

        eventAdapter = new EventAdapter(getContext(), events);

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        EventViewModel viewModel = new ViewModelProvider(this, factory).get(EventViewModel.class);

        viewModel.fetchUpcomingEvent().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result instanceof Result.Loading) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else if (result instanceof Result.Success) {
                    events = ((Result.Success<List<Event>>) result).getData();
                    setEventData(events);
                } else if (result instanceof Result.Error) {
                    binding.progressBar.setVisibility(View.GONE);
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

    private void setEventData(List<Event> events) {
        binding.progressBar.setVisibility(View.GONE);
        eventAdapter = new EventAdapter(getContext(), events);
        binding.rvEvent.setAdapter(eventAdapter);
    }
}