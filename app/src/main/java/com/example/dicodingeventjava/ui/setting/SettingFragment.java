package com.example.dicodingeventjava.ui.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.databinding.FragmentSettingBinding;
import com.example.dicodingeventjava.util.SharedPreferenceUtil;
import com.example.dicodingeventjava.viewmodel.DarkModeViewModel;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private SharedPreferenceUtil util;
    private DarkModeViewModel viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        util = new  SharedPreferenceUtil(requireContext());
        viewModel = new DarkModeViewModel();

        binding.swDarkMode.setOnCheckedChangeListener((buttonView, isDarkMode) -> {
            util.setDarkMode(isDarkMode);
            viewModel.setDarkMode(isDarkMode);
        });

        viewModel.setDarkMode(util.isDarkMode());
        viewModel.isDarkMode().observe(getViewLifecycleOwner(), isDarkMode -> {
            binding.swDarkMode.setChecked(isDarkMode);
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        return binding.getRoot();
    }
}