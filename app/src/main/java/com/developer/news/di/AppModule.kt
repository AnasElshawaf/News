package com.developer.news.di;

import android.app.Application
import com.developer.news.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideExecutors(): AppExecutors {
        return AppExecutors()
    }


}