package com.developer.news.di;

import com.developer.news.ui.news.NewsActivity
import com.developer.news.ui.news_details.NewsDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeNewsActivity(): NewsActivity

  @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeNewsDetailsActivity(): NewsDetailsActivity



}
