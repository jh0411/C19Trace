package com.example.c19trace.CheckIn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c19trace.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HistoryAdapter extends ListAdapter<HistoryClass, HistoryAdapter.HistoryViewHolder> {

    public HistoryAdapter(@NonNull DiffUtil.ItemCallback<HistoryClass> diffCallback) {
        super(diffCallback);
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTextView;
        private final TextView locationTextView;
        private final TextView timeTextView;

        private HistoryViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.tv_checkInDate);
            locationTextView = itemView.findViewById(R.id.tv_checkInLocation);
            timeTextView = itemView.findViewById(R.id.tv_checkInTime);
        }

        public void bind(String location, String date, String time) {
            locationTextView.setText(location);
            dateTextView.setText(date);
            timeTextView.setText(time);
        }

        static HistoryViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_setup, parent, false);
            return new HistoryViewHolder(view);
        }
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return HistoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        HistoryClass current = getItem(position);
        holder.bind(current.getLocation(),current.getDate(), current.getTime());
    }

    static class HistoryDiff extends DiffUtil.ItemCallback<HistoryClass> {

        @Override
        public boolean areItemsTheSame(@NonNull HistoryClass oldItem, @NonNull HistoryClass newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull HistoryClass oldItem, @NonNull HistoryClass newItem) {
            return oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getTime().equals(newItem.getTime());
        }
    }
}
