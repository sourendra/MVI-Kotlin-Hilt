package com.sourendra.kotlinwithhilt.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sourendra.kotlinwithhilt.R
import com.sourendra.kotlinwithhilt.model.Blog
import com.sourendra.kotlinwithhilt.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success<List<Blog>> ->{
                    displayProgressBar(false)
                    showData(dataState.data)
                }
                is DataState.Error ->{
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading ->{
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?){
        text.text = message ?: "Unknown Error"
    }

    private fun displayProgressBar(isLoading: Boolean){
        progress_bar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showData(blogs: List<Blog>){
        var sb = StringBuilder()
        for (blog in blogs){
            sb.append(blog.title).append("\n")
        }
        text.text = sb.toString()
    }
}