package com.example.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.pojo.Anime;

import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private List<Anime> animeList = new ArrayList<>();
    private OnReachEndListener onReachEndListener;
    private OnAnimeClickListener onAnimeClickListener;

    public void setAnimeList(List<Anime> animeList) {
        this.animeList = animeList;
        notifyDataSetChanged();
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnAnimeClickListener(OnAnimeClickListener onAnimeClickListener) {
        this.onAnimeClickListener = onAnimeClickListener;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_item,
                parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        Glide.with(holder.itemView)
                .load(anime.getPoster().getUrl())
                .into(holder.imageViewPoster);
        holder.textViewRating.setText(String.valueOf(anime.getRating().getKp()));

        if (position >= animeList.size() - 10 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAnimeClickListener != null) {
                    onAnimeClickListener.onAnimeClick(anime);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    interface OnAnimeClickListener {
        void onAnimeClick(Anime anime);
    }

    static class AnimeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPoster;
        private final TextView textViewRating;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }
}
