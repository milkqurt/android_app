package com.example.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.movies.pojo.Anime;
import com.example.movies.viewModel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private AnimeAdapter animeAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewAnime);
        progressBar = findViewById(R.id.progressBarLoading);
        animeAdapter = new AnimeAdapter();
        recyclerView.setAdapter(animeAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getAnimeList().observe(this, new Observer<List<Anime>>() {
            @Override
            public void onChanged(List<Anime> anime) {
                animeAdapter.setAnimeList(anime);
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        animeAdapter.setOnReachEndListener(new AnimeAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadAnime();
            }
        });
        animeAdapter.setOnAnimeClickListener(new AnimeAdapter.OnAnimeClickListener() {
            @Override
            public void onAnimeClick(Anime anime) {
                Intent intent = AnimeDetailActivity.newIntent(MainActivity.this, anime);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favouriteItem) {
            Intent intent = FavouriteAnimeActivity.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}