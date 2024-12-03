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

import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.ui.adapter.EventAdapter;
import com.example.dicodingeventjava.data.server.dto.EventDto;
import com.example.dicodingeventjava.databinding.FragmentFinishedBinding;
import com.example.dicodingeventjava.ui.viewmodel.FinishedViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

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

        TextInputLayout ilSearch = view.findViewById(R.id.ilSearch);
        TextInputEditText itSearch = view.findViewById(R.id.itSearch);

        ilSearch.setEndIconOnClickListener(v -> {
            dismissKeyboard(v);
            String inputText = Objects.requireNonNull(itSearch.getText()).toString();
            if (!inputText.isEmpty()) {
                finishedViewModel.searchFinishedEvent(inputText);
            } else {
                finishedViewModel.fetchFinishedEvent();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setEventData(List<EventDto> finishedEvents) {
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

    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}