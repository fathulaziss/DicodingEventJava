package com.example.dicodingeventjava.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dicodingeventjava.R;
import com.example.dicodingeventjava.data.response.ListEventsItem;
import com.example.dicodingeventjava.ui.detail.DetailEventActivity;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private final Context context;
    private final List<ListEventsItem> listEvent;

    public BannerAdapter(Context context, List<ListEventsItem> listEvent) {
        this.context = context;
        this.listEvent = listEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_banner_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListEventsItem eventItem = listEvent.get(position);
        Glide.with(context)
                .load(eventItem.getImageLogo()) // Image URL
                .into(holder.ivEventImage);
        holder.tvEventName.setText(eventItem.getName());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailEventActivity.class);
            intent.putExtra("id", eventItem.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivEventImage;
        private final TextView tvEventName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEventImage = itemView.findViewById(R.id.ivEventImage);
            tvEventName = itemView.findViewById(R.id.tvEventName);
        }
    }
}
