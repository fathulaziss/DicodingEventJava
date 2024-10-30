package com.example.dicodingeventjava.ui;

import android.content.Context;
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

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private final Context context;
    private final List<ListEventsItem> listEvent;

    public EventAdapter(Context context, List<ListEventsItem> listEvent) {
        this.context = context;
        this.listEvent = listEvent;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        ListEventsItem eventItem = listEvent.get(position);
        Glide.with(context)
                .load(eventItem.getImageLogo()) // Image URL
                .into(holder.ivEventImage);
        holder.tvEventName.setText(eventItem.getName());
        holder.tvEventCategory.setText(eventItem.getCategory());
        holder.tvEventLocation.setText(eventItem.getCityName());
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivEventImage;
        private final TextView tvEventName;
        private final TextView tvEventCategory;
        private final TextView tvEventLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEventImage = itemView.findViewById(R.id.ivEventImage);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventCategory = itemView.findViewById(R.id.tvEventCategory);
            tvEventLocation = itemView.findViewById(R.id.tvEventLocation);
        }
    }
}
