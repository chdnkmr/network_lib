package com.barkdev.network.di

import com.barkdev.netwrok.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Chandan Kumar on 2/6/2021.
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    fun provideLogInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            callTimeout(1, TimeUnit.MINUTES)
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            addConverterFactory(converterFactory)
            client(okHttpClient)
        }.build()
    }
}