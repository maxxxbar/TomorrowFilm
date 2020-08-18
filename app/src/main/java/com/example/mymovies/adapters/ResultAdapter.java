package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.entries.discover.movie.Result;
import com.squareup.picasso.Picasso;

public class ResultAdapter
        extends PagedListAdapter<Result, ResultAdapter.ResultAdapterViewHolder> {

    protected ResultAdapter(@NonNull DiffUtil.ItemCallback<Result> diffCallback) {
        super(diffCallback);
    }

    private MovieAdapter.OnPosterClickListener onPosterClickListener;

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    @NonNull
    @Override
    public ResultAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ResultAdapter.ResultAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapterViewHolder holder, int position) {
        Result result = getItem(position);
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        picasso.load(result.getPosterPath()).into(holder.imageViewSmallPoster);
    }

    class ResultAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewSmallPoster;

        public ResultAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
