package com.example.dicodingeventjava.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.data.local.entity.Event;
import com.example.dicodingeventjava.data.server.Result;
import com.example.dicodingeventjava.databinding.ActivityDetailEventBinding;
import com.example.dicodingeventjava.ui.viewmodel.EventViewModel;
import com.example.dicodingeventjava.ui.viewmodel.ViewModelFactory;

public class DetailEventActivity extends AppCompatActivity {

    private ActivityDetailEventBinding binding;
    private Boolean isFavoriteEvent;
    private Event event;

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

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        EventViewModel viewModel = new ViewModelProvider(this, factory).get(EventViewModel.class);

        Intent intent = getIntent();
        if (intent != null) {
            int eventId = intent.getIntExtra("id", -1);
            if (eventId != -1) {
                viewModel.fetchEventDetail(eventId).observe(this, result -> {
                    if (result != null) {
                        if (result instanceof Result.Loading) {
                            binding.pbDetailEvent.setVisibility(View.VISIBLE);
                            binding.btnFavorite.setVisibility(View.GONE);
                            binding.btnRegister.setVisibility(View.GONE);
                        } else if (result instanceof Result.Success) {
                            binding.pbDetailEvent.setVisibility(View.GONE);
                            binding.btnFavorite.setVisibility(View.VISIBLE);
                            binding.btnRegister.setVisibility(View.VISIBLE);
                            event = ((Result.Success<Event>) result).getData();
                            setDetailEventData(event);
                        } else if (result instanceof Result.Error) {
                            binding.pbDetailEvent.setVisibility(View.GONE);
                            binding.btnFavorite.setVisibility(View.GONE);
                            binding.btnRegister.setVisibility(View.GONE);
                            Toast.makeText(this, "Terjadi kesalahan: "+ ((Result.Error<?>) result).getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        binding.btnFavorite.setOnClickListener(view -> {
            if (event.getFavorite()) {
                viewModel.removeFavoriteEvent(event);
            } else {
                viewModel.setFavoriteEvent(event);
            }
        });
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
        binding.tvEventQuota.setText(String.format("Quota Tersedia : %s", event.getQuota() - event.getRegistrants()));
        binding.tvEventDescription.setText(Html.fromHtml(event.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        Glide.with(DetailEventActivity.this)
                .load(event.getMediaCover()) // Image URL
                .into(binding.ivEventImage);
        binding.btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getLink()));
            startActivity(intent);
        });
        if (event.getFavorite()) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_outline_black_24dp);
        }
    }
}