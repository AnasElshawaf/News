package com.developer.news.ui.news;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.developer.news.api.NewsClient;
import com.developer.news.model.News;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.developer.news.util.MainConstants.API_KEY;
import static com.developer.news.util.MainConstants.ASSOCIATED_PRESS;
import static com.developer.news.util.MainConstants.THE_NEXT_WEB;

public class NewsViewModel extends ViewModel {

    MutableLiveData<News> newsMutableLiveData = new MutableLiveData<>();

    public void getNews() {

        Observable<News> observableNextWeb = NewsClient.getINSTANCE().getArticles(THE_NEXT_WEB, API_KEY)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observable<News> observableAssociatedPress = NewsClient.getINSTANCE().getArticles(ASSOCIATED_PRESS, API_KEY)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observable.merge(observableNextWeb, observableAssociatedPress)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> newsMutableLiveData.setValue(o), o -> Timber.e(o));


//        observableNextWeb.subscribe(o -> newsMutableLiveData.setValue(o), o -> Timber.tag("error").e(o.toString()));
    }
}
