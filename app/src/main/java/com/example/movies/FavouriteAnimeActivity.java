package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.movies.pojo.Anime;
import com.example.movies.viewModel.FavouritesAnimeViewModel;

import java.util.List;

public class FavouriteAnimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_anime);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAnimeFavourites);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        AnimeAdapter animeAdapter = new AnimeAdapter();
        recyclerView.setAdapter(animeAdapter);
        animeAdapter.setOnAnimeClickListener(new AnimeAdapter.OnAnimeClickListener() {
            @Override
            public void onAnimeClick(Anime anime) {
                Intent intent = AnimeDetailActivity.newIntent(FavouriteAnimeActivity.this, anime);
                startActivity(intent);
            }
        });
        FavouritesAnimeViewModel viewModel = new ViewModelProvider(this)
                .get(FavouritesAnimeViewModel.class);
        viewModel.getAnime().observe(this, new Observer<List<Anime>>() {
            @Override
            public void onChanged(List<Anime> anime) {
                animeAdapter.setAnimeList(anime);
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteAnimeActivity.class);
    }
}