package com.example.dicodingeventjava.ui.finished;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dicodingeventjava.databinding.FragmentFinishedBinding;

public class FinishedFragment extends Fragment {

    private FragmentFinishedBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        FinishedViewModel finishedViewModel =
                new ViewModelProvider(this).get(FinishedViewModel.class);

        binding = FragmentFinishedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvFinished;
        finishedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}