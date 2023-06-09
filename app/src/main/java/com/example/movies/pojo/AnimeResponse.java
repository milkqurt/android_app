package com.example.movies.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeResponse {

    @SerializedName("docs")
    private List<Anime> animeList;

    public AnimeResponse(List<Anime> animeList) {
        this.animeList = animeList;
    }

    public List<Anime> getAnimeList() {
        return animeList;
    }

    @Override
    public String toString() {
        return "AnimeResponse{" +
                "animeList=" + animeList +
                '}';
    }
}
