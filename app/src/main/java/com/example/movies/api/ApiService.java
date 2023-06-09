package com.example.movies.api;

import com.example.movies.pojo.AnimeResponse;
import com.example.movies.pojo.ReviewResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v1.3/movie?sortField=rating.kp&sortType=-1&limit=30&type=anime&token=P14VS77-9P84DB1-NF3FB6M-A26XPRM&sortField=votes.kp")
    Single<AnimeResponse> loadAnime(@Query("page") int page);

    @GET("https://api.kinopoisk.dev/v1/review?token=P14VS77-9P84DB1-NF3FB6M-A26XPRM&field=movieId")
    Single<ReviewResponse> loadReviews(@Query("search") int id);

}
