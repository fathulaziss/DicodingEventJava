package com.example.dicodingeventjava.ui.upcoming;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.databinding.FragmentFinishedBinding;
import com.example.dicodingeventjava.databinding.FragmentUpcomingBinding;

public class UpcomingFragment extends Fragment {

    private FragmentUpcomingBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        UpcomingViewModel upcomingViewModel =
                new ViewModelProvider(this).get(UpcomingViewModel.class);

        binding = FragmentUpcomingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvUpcoming;
        upcomingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}