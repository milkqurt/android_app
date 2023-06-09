package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movies.pojo.Anime;
import com.example.movies.pojo.Review;
import com.example.movies.viewModel.AnimeDetailViewModel;

import java.util.List;

public class AnimeDetailActivity extends AppCompatActivity {

    private static final String EXTRA_ANIME = "anime";
    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private AnimeDetailViewModel viewModel;
    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private ImageView imageViewStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);
        initViews();
        viewModel = new ViewModelProvider(this).get(AnimeDetailViewModel.class);
        Anime anime = (Anime) getIntent().getSerializableExtra(EXTRA_ANIME);
        reviewAdapter = new ReviewAdapter();
        recyclerView.setAdapter(reviewAdapter);
        Glide.with(this)
                .load(anime.getPoster().getUrl())
                .into(imageViewPoster);
        textViewTitle.setText(anime.getName());
        textViewYear.setText(String.valueOf(anime.getYear()));
        textViewDescription.setText(anime.getDescription());
        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });
        viewModel.loadReviews(anime.getId());
        Drawable starOff = ContextCompat.getDrawable(AnimeDetailActivity.this, android.R.drawable.star_off);
        Drawable starOn = ContextCompat.getDrawable(AnimeDetailActivity.this, android.R.drawable.star_on);
        viewModel.getFavouriteAnime(anime.getId()).observe(this, new Observer<Anime>() {
            @Override
            public void onChanged(Anime animeFromDb) {
                if (animeFromDb == null) {
                    imageViewStar.setImageDrawable(starOff);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insertAnime(anime);
                        }
                    });
                } else {
                    imageViewStar.setImageDrawable(starOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeAnime(anime.getId());
                        }
                    });
                }
            }
        });
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
        recyclerView = findViewById(R.id.recyclerViewReviews);
        imageViewStar = findViewById(R.id.imageViewStar);
    }

    public static Intent newIntent(Context context, Anime anime) {
        Intent intent = new Intent(context, AnimeDetailActivity.class);
        intent.putExtra(EXTRA_ANIME, anime);
        return intent;
    }
}