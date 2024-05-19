package com.halan.utils.di

import com.halan.utils.config.EnvironmentConfig
import com.halan.utils.config.EnvironmentConfigImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    @Binds
    @Singleton
    abstract fun bindEnvironmentConfig(
        environmentConfigImpl: EnvironmentConfigImpl
    ): EnvironmentConfig
}