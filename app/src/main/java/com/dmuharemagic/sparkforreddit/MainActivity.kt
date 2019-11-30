package com.dmuharemagic.sparkforreddit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmuharemagic.sparkforreddit.model.RedditNews
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PostsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var loadingIndicator: ContentLoadingProgressBar

    private var redditNews: RedditNews? = null

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(RedditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = PostsAdapter()

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addOnScrollListener(InfiniteScrollListener({ viewModel.getPostsCached(redditNews?.after.orEmpty()) },
                viewManager as LinearLayoutManager
            ))
        }

        loadingIndicator = findViewById(R.id.loadingIndicator)

        viewModel.users.observe(this, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {
                    if(it.data != null) {
                        loadingIndicator.hide()
                        recyclerView.visibility = View.VISIBLE
                        redditNews = it.data
                        println(redditNews)
                        viewAdapter.update(it.data.data!!)
                    } else {
                        loadingIndicator.show()
                    }
                }
                Resource.Status.SUCCESS -> {
                    loadingIndicator.hide()
                    recyclerView.visibility = View.VISIBLE
                    redditNews = it.data
                    println(redditNews)
                    viewAdapter.update(it.data!!.data!!)
                }
                Resource.Status.ERROR -> {
                    loadingIndicator.hide()
                    Snackbar.make(recyclerView, it.error?.message.orEmpty(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}
