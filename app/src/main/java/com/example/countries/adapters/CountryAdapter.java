package com.example.countries.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.countries.R;
import com.example.countries.databinding.CountryListItemBinding;
import com.example.countries.model.Country;

import java.util.concurrent.atomic.AtomicBoolean;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CountryListItemBinding.inflate(LayoutInflater.from(parent.getContext())
                , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Country country = (Country) asyncListDiffer.getCurrentList().get(position);
        holder.binding.tvCountry.setText(String.valueOf(country.getName()));
        holder.binding.tvCapital.setText(String.valueOf(country.getCapital()));
        holder.binding.tvRegion.setText(String.valueOf(country.getRegion()));

        Glide.with(holder.itemView).load(country.getFlags().get(1)).into(holder.binding.civCountryImage);

        holder.binding.tvPopulation.setText(String.valueOf(country.getPopulation()));
        holder.binding.tvBorders.setText(String.valueOf(country.getBorders()));
        AtomicBoolean isExpanded = new AtomicBoolean(true);
        holder.binding.clItems.setOnClickListener(view -> {

            Animation expand = AnimationUtils.loadAnimation(view.getContext(), R.anim.expand_anim);
            if (isExpanded.get()) {
                holder.binding.clExpandable.setVisibility(View.VISIBLE);
                holder.binding.clExpandable.startAnimation(expand);
                isExpanded.set(false);
            } else holder.binding.clExpandable.setVisibility(View.GONE);


        });
    }

    @Override
    public int getItemCount() {
        return asyncListDiffer.getCurrentList().size();
    }

    private static final DiffUtil.ItemCallback<Country> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Country>() {
                @Override
                public boolean areItemsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
                    return oldItem.getFlags().get(0).equals(newItem.getFlags().get(0));
                }

                @Override
                public boolean areContentsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
                    return oldItem.getFlags().get(0).equals(newItem.getFlags().get(0));
                }
            };

    public AsyncListDiffer asyncListDiffer = new AsyncListDiffer(this, DIFF_CALLBACK);

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CountryListItemBinding binding;

        public MyViewHolder(CountryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
