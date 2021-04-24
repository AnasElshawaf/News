package com.developer.news.di;


import com.developer.news.ThisApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
    ]
)
interface AppComponent {
    fun inject(app: ThisApp)

}