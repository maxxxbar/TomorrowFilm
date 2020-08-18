package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.entries.discover.trailer.Result;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{

    private List<Result> trailerList = new ArrayList<>();
    private OnClickWatchTrailer onClickWatchTrailer;

    public void setTrailerList(List<Result> trailerList) {
        this.trailerList = trailerList;
    }

    public interface OnClickWatchTrailer {
        void onTrailerClick(int position);
    }

    public void setOnClickWatchTrailer(OnClickWatchTrailer onClickWatchTrailer) {
        this.onClickWatchTrailer = onClickWatchTrailer;
    }

    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        Result result = trailerList.get(position);
        holder.textViewVideoName.setText(result.getName());

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    class TrailerAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView textViewVideoName;

        public TrailerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewVideoName = itemView.findViewById(R.id.textViewVideoName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickWatchTrailer != null) {
                        onClickWatchTrailer.onTrailerClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
