package com.api.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.api.sample.databinding.ListItemBinding;

import java.util.Map;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    private Map<String, String> list;

    private LayoutInflater mInflater;

    public CountriesAdapter(Context context, Map<String, String> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListItemBinding.inflate(mInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String titleText = list.get(String.valueOf(position));
        holder.listItemBinding.title.setText(titleText);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding listItemBinding;
        TextView textView;

        ViewHolder(@NonNull ListItemBinding binding) {
            super(binding.getRoot());
            listItemBinding = binding;
            textView = binding.title;
        }
    }
}
