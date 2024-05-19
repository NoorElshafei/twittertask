package com.halan.utils.config

interface EnvironmentConfig {

    fun getBaseUrl(): String
    fun getConsumerKey(): String
    fun getConsumerSecret(): String
}
