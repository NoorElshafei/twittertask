package com.halan.twitter.data.utils


import com.halan.twitter.data.response.TweetData
import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse


object Mocks {

    val responseAuth = TwitterAccessTokenResponse(
       token_type = "",
        access_token = ""
    )

    val tweetData = TweetData(id="", text = "")

    val responsePostTweet = TweetResponse(
      data= tweetData
    )

    val text = "nooor"
    val accessToken = "test"

    val auth = "test"
    val grantType ="test"

    val exceptionPostTweet = Throwable("Can't post tweet, please try again later.")
    val exceptionAuth = Throwable("Can't get Access token, please try again later.")

}
