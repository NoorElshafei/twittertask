package com.halan.twitter.presentation.post_tweet

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.halan.feature.twitter.R
import com.halan.feature.twitter.databinding.FragmentTwitterBinding
import com.halan.network.data.errors.getMessage
import com.halan.network.data.errors.getType
import com.halan.twitter.domain.enums.TweetValidation
import com.halan.ui.extensions.hide
import com.halan.ui.extensions.show
import com.halan.ui.presentation.BaseFragment
import com.halan.utils.extensions.collect
import com.halan.utils.extensions.observe
import com.halan.utils.states.DataState

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostTweetFragment : BaseFragment<FragmentTwitterBinding>(FragmentTwitterBinding::inflate) {

    private val viewModel by viewModels<PostTweetViewModel>()

    override fun bindViews() {
        initUI()
        subscribeOnObservers()
    }


    private fun initUI() {
        binding.tweetInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val charCount = s?.length ?: 0
                binding.charactersTyped.text = "$charCount/280"
                binding.charactersRemaining.text = (280 - charCount).toString()
                binding.postTweet.isEnabled = charCount in 1..280
            }
        })

        binding.copyText.setOnClickListener {
            val textToCopy = binding.tweetInput.text.toString()
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("tweet", textToCopy)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Text copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        binding.clearText.setOnClickListener {
            binding.tweetInput.setText("")
        }

        binding.postTweet.setOnClickListener {
            viewModel.authenticate(binding.tweetInput.text.toString())
        }
    }

    private fun subscribeOnObservers() {
        observe(viewModel.validationTweet) { showValidation(it) }
        collect(viewModel.authDataState) {
            when (it) {
                is DataState.Success -> {
                    hideLoading()
                    showMessage("authenticate successful")
                    viewModel.postTweet(
                        binding.tweetInput.text.toString(),
                        it.data.access_token
                    )
                }

                is DataState.Failure -> {
                    hideLoading()
                    showMessage(
                        it.throwable.getType().getMessage().text ?: "something wrong, try again"
                    )
                }

                DataState.Loading -> showLoading()

                DataState.None -> {}
            }
        }
        collect(viewModel.postTweetDataState) {
            when (it) {
                is DataState.Success -> {
                    hideLoading()
                    showMessage("tweet added successfully")
                }

                is DataState.Failure -> {
                    hideLoading()
                    showMessage(
                        it.throwable.getType().getMessage().text ?: "something wrong, try again"
                    )
                }

                DataState.Loading -> showLoading()

                DataState.None -> {}
            }
        }

    }

    private fun showValidation(invalidField: Int?) {
        invalidField?.let {
            when (it) {
                TweetValidation.EMPTY_TWEET.value -> {
                    showMessage("You must add text")
                }
            }
        }
    }

    private fun hideLoading() {
        binding.loadingLayout.loading.hide()
    }

    private fun showLoading() {
        binding.loadingLayout.loading.show()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_twitter

}
