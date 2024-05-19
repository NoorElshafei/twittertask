package com.halan.twitter.data.response
import com.google.gson.annotations.SerializedName


/**
 * @Created_by: Noor Elshafei
 * @Date: 4/20/2024
 */


data class TweetResponse(val data: TweetData)
data class TweetData(val id: String, val text: String)

