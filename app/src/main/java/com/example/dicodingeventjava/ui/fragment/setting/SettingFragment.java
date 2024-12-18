package com.example.dicodingeventjava.ui.fragment.setting;

import android.os.Bundle;
import android.os.Build;
import android.Manifest;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.databinding.FragmentSettingBinding;
import com.example.dicodingeventjava.ui.viewmodel.ViewModelFactory;
import com.example.dicodingeventjava.utils.MyWorker;
import com.example.dicodingeventjava.utils.SharedPreferenceUtil;
import com.example.dicodingeventjava.ui.viewmodel.DarkModeViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private SharedPreferenceUtil util;
    private WorkManager workManager;
    private PeriodicWorkRequest periodicWorkRequest;
    private Event event;
    private DarkModeViewModel viewModel;
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

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        util = new  SharedPreferenceUtil(requireContext());
        ViewModelFactory factory = ViewModelFactory.getInstance(requireContext());
        viewModel = new ViewModelProvider(this, factory).get(DarkModeViewModel.class);

        workManager = WorkManager.getInstance(requireContext());

        viewModel.fetchActiveEvent().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result instanceof Result.Loading) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else if (result instanceof Result.Success) {
                    binding.progressBar.setVisibility(View.GONE);
                    List<Event> events = ((Result.Success<List<Event>>) result).getData();
                    if (!events.isEmpty()) {
                        event = events.get(0);
                    }
                } else if (result instanceof Result.Error) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Terjadi kesalahan: "+ ((Result.Error<?>) result).getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }

        binding.swDarkMode.setOnCheckedChangeListener((buttonView, isDarkMode) -> {
            util.setDarkMode(isDarkMode);
            viewModel.setDarkMode(isDarkMode);
        });

        binding.swNotification.setOnCheckedChangeListener((buttonView, isNotificationActive) -> {
            Log.d("SettingFragment", "isNotificationActive: " + isNotificationActive);
            binding.swNotification.setChecked(!isNotificationActive);
//            if (!isNotificationActive) {
//                startPeriodicTask();
//            } else {
//                cancelPeriodicTask();
//            }
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

        viewModel.setNotification(util.isNotification());
        viewModel.isNotification().observe(getViewLifecycleOwner(), isNotificationActive -> binding.swNotification.setChecked(isNotificationActive));

        return binding.getRoot();
    }

    private void startPeriodicTask() {
        binding.textStatus.setText(getString(R.string.status));

        Data data = new Data.Builder()
                .putString(MyWorker.EXTRA_CITY, "Jakarta")
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        workManager.enqueue(periodicWorkRequest);

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(getViewLifecycleOwner(), workInfo -> {
                    String status = workInfo.getState().name();
                    binding.textStatus.append("\n"+status);
                    binding.btnCancelTask.setEnabled(false);
                    if (workInfo.getState() == WorkInfo.State.ENQUEUED){
                        binding.btnCancelTask.setEnabled(true);
                    }
                });
    }

    private void cancelPeriodicTask() {
        workManager.cancelWorkById(periodicWorkRequest.getId());
        util.setNotification(false);
        viewModel.setNotification(false);
    }
}