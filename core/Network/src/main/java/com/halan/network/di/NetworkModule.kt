package com.halan.network.di

import android.content.Context
import com.halan.core.utils.BuildConfig
import com.halan.network.utils.CacheInterceptor
import com.halan.utils.config.EnvironmentConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


private const val REQUEST_TIME_OUT: Long = 60

/**
 * @Created_by: Noor Elshafei
 * @Date: 4/20/2024
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        environmentConfig: EnvironmentConfig
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(environmentConfig.getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()



    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        httpClientBuilder.addInterceptor(headerInterceptor)
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        httpClientBuilder.cache(cache)
        httpClientBuilder.addNetworkInterceptor(CacheInterceptor())
        //  httpClientBuilder.addInterceptor(ForceCacheInterceptor(context))
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addNetworkInterceptor(loggingInterceptor)
        }
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val originalRequest = chain.request()
            val url: HttpUrl = originalRequest.url.newBuilder()
                .build()
            val newRequest = originalRequest.newBuilder().url(url).build()
            chain.proceed(newRequest)
        }

}