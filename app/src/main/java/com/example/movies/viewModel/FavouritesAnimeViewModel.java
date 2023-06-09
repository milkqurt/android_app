package com.example.movies.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.dao.AnimeDao;
import com.example.movies.database.AnimeDatabase;
import com.example.movies.pojo.Anime;

import java.util.List;

public class FavouritesAnimeViewModel extends AndroidViewModel {

    private final AnimeDao animeDao;

    public FavouritesAnimeViewModel(@NonNull Application application) {
        super(application);
        animeDao = AnimeDatabase.getInstance(application).animeDao();
    }

    public LiveData<List<Anime>> getAnime() {
        return animeDao.getAllFavouriteAnime();
    }
}
