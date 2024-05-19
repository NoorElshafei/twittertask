package com.halan.twitter.presentation.post_tweet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halan.utils.extensions.catchError
import com.halan.utils.states.DataState
import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse
import com.halan.twitter.domain.enums.TweetValidation
import com.halan.twitter.domain.usecase.TweetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PostTweetViewModel @Inject constructor(
    private val tweetUseCase: TweetUseCase,
) : ViewModel() {

    private val _postTweetDataState: MutableStateFlow<DataState<TweetResponse>> =
        MutableStateFlow(DataState.Loading)
    val postTweetDataState: StateFlow<DataState<TweetResponse>> get() = _postTweetDataState

    private val _authDataState: MutableStateFlow<DataState<TwitterAccessTokenResponse>> =
        MutableStateFlow(DataState.None)
    val authDataState: StateFlow<DataState<TwitterAccessTokenResponse>> get() = _authDataState

    private val validationTweetLiveData = MutableLiveData<Int>()
    val validationTweet: LiveData<Int> get() = validationTweetLiveData

    fun postTweet(text: String, accessToken: String) {
        viewModelScope.launch {
            tweetUseCase.postTweet(text,accessToken)
                .catchError { _postTweetDataState.emit(DataState.Failure(it)) }
                .collect { _postTweetDataState.emit(DataState.Success(it)) }
        }
    }
    fun authenticate(text: String) {
        if (validateForecast(text)) {
        viewModelScope.launch {
            tweetUseCase.getAccessToken()
                .catchError { _authDataState.emit(DataState.Failure(it)) }
                .collect { _authDataState.emit(DataState.Success(it)) }
        }
        }
    }

    private fun validateForecast(
        text: String?
    ): Boolean {
        return if (text.isNullOrEmpty()) {
            validationTweetLiveData.value = TweetValidation.EMPTY_TWEET.value
            false
        } else {
            true
        }

    }


}
