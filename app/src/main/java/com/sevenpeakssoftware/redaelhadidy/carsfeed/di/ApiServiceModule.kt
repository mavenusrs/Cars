package com.sevenpeakssoftware.redaelhadidy.carsfeed.di

import com.google.gson.GsonBuilder
import com.sevenpeakssoftware.redaelhadidy.data.BASE_URL
import com.sevenpeakssoftware.redaelhadidy.data.remote.ArticleFeedApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiServiceModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        okHttpClientBuilder.addInterceptor(logging)

        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleFeedApi(retrofit: Retrofit): ArticleFeedApi {
        return retrofit.create(ArticleFeedApi::class.java)
    }
}