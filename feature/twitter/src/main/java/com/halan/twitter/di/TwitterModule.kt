package com.halan.twitter.di

import com.halan.twitter.data.data_source.TwitterService
import com.halan.twitter.domain.repository.TwitterRepository
import com.halan.twitter.domain.repository.TwitterRepositoryImpl
import com.halan.twitter.domain.usecase.TweetUseCase
import com.halan.twitter.domain.usecase.TweetUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object TwitterModule {

    @Provides
    @Singleton
    fun provideService(
        retrofit: Retrofit
    ): TwitterService = retrofit.create(TwitterService::class.java)


}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingTwitterModule {

    @Binds
    @Singleton
    abstract fun bindTwitterRepository(
        twitterRepositoryImpl: TwitterRepositoryImpl
    ): TwitterRepository

    @Binds
    @Singleton
    abstract fun bindUseCase(
        tweetUseCaseImpl: TweetUseCaseImpl
    ): TweetUseCase


}
