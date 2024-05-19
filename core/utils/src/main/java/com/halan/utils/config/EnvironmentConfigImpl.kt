package com.halan.utils.config


import com.halan.core.utils.BuildConfig
import javax.inject.Inject

class EnvironmentConfigImpl @Inject constructor() : EnvironmentConfig {

    override fun getBaseUrl(): String = BuildConfig.BASE_URL
    override fun getConsumerKey(): String = BuildConfig.CONSUMER_KEY
    override fun getConsumerSecret(): String = BuildConfig.CONSUMER_SECRET

}
