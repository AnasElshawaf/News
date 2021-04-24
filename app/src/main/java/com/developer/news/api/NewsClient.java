package com.developer.news.api;


import com.developer.news.model.News;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {
    private static final String BASE_URL = "https://newsapi.org/";
    private NewsInterface newsInterface;
    private static NewsClient INSTANCE;

    public NewsClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        newsInterface = retrofit.create(NewsInterface.class);
    }

    public static NewsClient getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new NewsClient();
        }
        return INSTANCE;
    }

    public Observable<News> getArticles(String source, String apiKey) {
        return newsInterface.getArticles(source, apiKey);
    }
}
