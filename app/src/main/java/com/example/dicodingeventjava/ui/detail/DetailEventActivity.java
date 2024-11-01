package com.example.dicodingeventjava.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.data.response.Event;
import com.example.dicodingeventjava.databinding.ActivityDetailEventBinding;

public class DetailEventActivity extends AppCompatActivity {

    private ActivityDetailEventBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityDetailEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_700));

        DetailEventViewModel viewModel = new ViewModelProvider(this).get(DetailEventViewModel.class);
        viewModel.getDetailEvent().observe(DetailEventActivity.this, this::setDetailEventData);
        viewModel.isLoadingDetailEvent().observe(DetailEventActivity.this, this::showLoading);

        Intent intent = getIntent();
        if (intent != null) {
            int eventId = intent.getIntExtra("id", -1);
            if (eventId != -1) {
                viewModel.fetchDetailEvent(eventId);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setDetailEventData(Event event) {
        binding.tvEventName.setText(event.getName());
        binding.tvEventOwner.setText(String.format("Presented By : %s", event.getOwnerName()));
        binding.tvEventSchedule.setText(String.format("Date : %s", event.getBeginTime()));
        binding.tvEventQuota.setText(String.format("Quota : %s", event.getQuota()));
        binding.tvEventDescription.setText(Html.fromHtml(event.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        Glide.with(DetailEventActivity.this)
                .load(event.getMediaCover()) // Image URL
                .into(binding.ivEventImage);
        binding.btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getLink()));
            startActivity(intent);
        });
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.pbDetailEvent.setVisibility(View.VISIBLE);
            binding.btnRegister.setVisibility(View.GONE);
        } else {
            binding.pbDetailEvent.setVisibility(View.GONE);
            binding.btnRegister.setVisibility(View.VISIBLE);
        }
    }
}