package com.example.movies.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.api.ApiFactory;
import com.example.movies.pojo.Anime;
import com.example.movies.pojo.AnimeResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    private final MutableLiveData<List<Anime>> animeList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
        loadAnime();
    }

    public LiveData<List<Anime>> getAnimeList() {
        return animeList;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadAnime() {
        Boolean loading = isLoading.getValue();
        if (loading != null && loading) {
            return;
        }
        Disposable disposable = ApiFactory.apiService.loadAnime(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<AnimeResponse>() {
                    @Override
                    public void accept(AnimeResponse animeResponse) throws Throwable {
                        List<Anime> loadedAnime = animeList.getValue();
                        if (loadedAnime != null) {
                            loadedAnime.addAll(animeResponse.getAnimeList());
                            animeList.setValue(loadedAnime);
                        } else {
                            animeList.setValue(animeResponse.getAnimeList());
                        }
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
