package com.example.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.pojo.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>{

    private static final String TYPE_POSITIVE = "Позитивный";
    private static final String TYPE_NEUTRAL = "Нейтральный";
    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item,
                        parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.authorTextView.setText(review.getAuthor());
        holder.reviewTextView.setText(review.getReview());
        String type = review.getType();
        int colorResId = android.R.color.holo_red_dark;
        switch (type) {
            case TYPE_POSITIVE:
                colorResId = android.R.color.holo_green_dark;
                break;
            case TYPE_NEUTRAL:
                colorResId = android.R.color.holo_orange_dark;
                break;
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.linearLayout.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayout;
        private final TextView authorTextView;
        private final TextView reviewTextView;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayoutReview);
            authorTextView = itemView.findViewById(R.id.textViewAuthor);
            reviewTextView = itemView.findViewById(R.id.textViewReview);
        }
    }
}
