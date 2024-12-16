package com.example.dicodingeventjava.ui.fragment.finished;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.ui.adapter.EventAdapter;
import com.example.dicodingeventjava.databinding.FragmentFinishedBinding;
import com.example.dicodingeventjava.ui.viewmodel.EventViewModel;
import com.example.dicodingeventjava.ui.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FinishedFragment extends Fragment {

    private FragmentFinishedBinding binding;
    private EventAdapter eventAdapter;
    private EventViewModel viewModel;
    private List<Event> events = new ArrayList<>();

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

        eventAdapter = new EventAdapter(getContext(), events);

        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
        viewModel = new ViewModelProvider(this, factory).get(EventViewModel.class);

        viewModel.fetchFinishedEvent().observe(getViewLifecycleOwner(), result -> {
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

        binding.ilSearch.setEndIconOnClickListener(v -> {
            dismissKeyboard(v);
            String inputText = Objects.requireNonNull(binding.itSearch.getText()).toString();
            if (!inputText.isEmpty()) {
                viewModel.searchFinishedEvent(inputText);
            } else {
                viewModel.fetchFinishedEvent();
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

    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}