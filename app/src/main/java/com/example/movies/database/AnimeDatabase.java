package com.example.movies.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movies.dao.AnimeDao;
import com.example.movies.pojo.Anime;

@Database(entities = {Anime.class}, version = 1, exportSchema = false)
public abstract class AnimeDatabase extends RoomDatabase {

    private static final String DB_NAME = "anime.db";
    private static AnimeDatabase instance;

    public static AnimeDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application,
                    AnimeDatabase.class,
                    DB_NAME).build();
        }
        return instance;
    }

   public abstract AnimeDao animeDao();
}
