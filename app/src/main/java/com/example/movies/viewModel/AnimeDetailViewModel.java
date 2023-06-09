package com.example.movies.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.api.ApiFactory;
import com.example.movies.api.ApiService;
import com.example.movies.dao.AnimeDao;
import com.example.movies.database.AnimeDatabase;
import com.example.movies.pojo.Anime;
import com.example.movies.pojo.Review;
import com.example.movies.pojo.ReviewResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimeDetailViewModel extends AndroidViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private final AnimeDao animeDao;

    public AnimeDetailViewModel(@NonNull Application application) {
        super(application);
        animeDao = AnimeDatabase.getInstance(application).animeDao();
    }

    public LiveData<Anime> getFavouriteAnime(int animeId) {
        return animeDao.getFavouriteAnim(animeId);
    }

    public MutableLiveData<List<Review>> getReviews() {
        return reviews;
    }

    public void loadReviews(int id) {
        Disposable disposable = ApiFactory.apiService.loadReviews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviews();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> review) throws Throwable {
                        reviews.setValue(review);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);
    }

    public void insertAnime(Anime anime) {
        Disposable disposable = animeDao.insertAnime(anime)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeAnime(int animeId) {
        Disposable disposable = animeDao.removeAnime(animeId)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
