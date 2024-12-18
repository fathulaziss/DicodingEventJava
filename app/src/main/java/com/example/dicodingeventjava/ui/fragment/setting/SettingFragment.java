package com.example.dicodingeventjava.ui.fragment.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Build;
import android.Manifest;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dicodingeventjava.databinding.FragmentSettingBinding;
import com.example.dicodingeventjava.utils.MyWorker;
import com.example.dicodingeventjava.utils.SharedPreferenceUtil;

import java.util.concurrent.TimeUnit;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private SharedPreferenceUtil util;
    private WorkManager workManager;
    private PeriodicWorkRequest periodicWorkRequest;
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean isGranted) {
                            if (isGranted) {
                                Toast.makeText(requireContext(), "Notifications permission granted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Notifications permission rejected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        util = new  SharedPreferenceUtil(requireContext());

        workManager = WorkManager.getInstance(requireContext());

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }

        binding.swDarkMode.setChecked(util.isDarkMode());
        binding.swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                util.setDarkMode(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                util.setDarkMode(false);
            }
        });

        binding.swNotification.setChecked(util.isNotification());
        binding.swNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startPeriodicTask();
            } else {
                cancelPeriodicTask();
            }
        });

        return binding.getRoot();
    }

    private void startPeriodicTask() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        workManager.enqueue(periodicWorkRequest);

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(getViewLifecycleOwner(), workInfo -> {
                    if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
                        util.setNotificationId(periodicWorkRequest.getId());
                        util.setNotification(true);
                        binding.swNotification.setChecked(true);
                    } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                        util.setNotification(false);
                        binding.swNotification.setChecked(false);
                    }
                });
    }

    private void cancelPeriodicTask() {
        if (util.getNotificationId() != null) {
            workManager.cancelWorkById(util.getNotificationId());
            util.setNotification(false);
        }
    }
}