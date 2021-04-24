package com.developer.news.api;

import com.developer.news.model.News;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {

    @GET("v1/articles?")
    public Observable<News> getArticles(@Query("source") String source,
                                        @Query("apiKey") String apiKey);
}
