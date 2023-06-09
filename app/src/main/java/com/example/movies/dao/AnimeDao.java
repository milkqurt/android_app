package com.example.movies.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.pojo.Anime;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface AnimeDao {

    @Query("SELECT * FROM favourite_anime")
    LiveData<List<Anime>> getAllFavouriteAnime();

    @Query("SELECT * FROM favourite_anime WHERE id = :animeId")
    LiveData<Anime> getFavouriteAnim(int animeId);

    @Insert
    Completable insertAnime(Anime anime);

    @Query("DELETE FROM favourite_anime WHERE id = :animeId")
    Completable removeAnime(int animeId);
}
