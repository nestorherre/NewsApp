package com.example.newsapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.model.data.Article
import com.example.newsapp.model.repository.Repository
import com.example.newsapp.utils.Constants.Companion.SERVER_ERROR
import com.example.newsapp.utils.Constants.Companion.VISIBLE
import com.example.newsapp.utils.Formatter
import com.example.newsapp.view.adapters.RecyclerViewAdapter
import com.example.newsapp.viewmodel.NewsHomeVMFactory
import com.example.newsapp.viewmodel.NewsHomeViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_home.*

class NewsHome : AppCompatActivity() {

    private lateinit var myAdapter: RecyclerViewAdapter
    private lateinit var viewModel: NewsHomeViewModel
    private lateinit var featuredArticleURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_home)

        initComponents()
        setObservers()
        setClickListeners()
        setSwipeToRefresh()
        viewModel.getNews()
    }

    private fun setSwipeToRefresh() {
        swipeToRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        swipeToRefresh.setOnRefreshListener {
            viewModel.getNews()
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun setClickListeners() {
        ivNewsFeatured.setOnClickListener {
            openWebPage(featuredArticleURL)
        }

        btnTryAgain.setOnClickListener {
            viewModel.getNews()
        }
    }


    private fun initComponents() {
        val repository = Repository()
        val viewModelFactory = NewsHomeVMFactory(repository, this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsHomeViewModel::class.java)
        setupRecyclerView()
    }

    private fun setObservers() {
        viewModel.myResponse.observe(this, Observer { articles ->
            myAdapter.setData(articles)
        })

        viewModel.featuredArticle.observe(this, Observer { article ->
            setFeaturedArticleValues(article)
        })

        viewModel.loading.observe(this, Observer { visibility ->
            pbLoading.visibility = visibility
        })

        viewModel.displayHomeViews.observe(this, Observer { visibility ->
            displayHomeViews(visibility)
        })

        viewModel.displayErrorViews.observe(this, Observer { visibility ->
            displayErrorViews(visibility)
        })

    }

    private fun setFeaturedArticleValues(article: Article) {
        featuredArticleURL = article.url
        Picasso.get().load(article.urlToImage).into(ivNewsFeatured)
        tvTitleFeatured.text = Formatter.stringLength(article.title, 70)
        tvSourceFeatured.text = article.source.name
        tvTimeFeatured.text = Formatter.date(article.publishedAt)
        tvAuthorFeatured.text = Formatter.stringLength(article.author, 22)
    }

    private fun displayErrorViews(visibility: Int) {
        if (visibility == SERVER_ERROR){
            ivConnectionError.visibility = VISIBLE
            tvConnectionFailed.visibility = VISIBLE
            tvNoConnectionMessage.visibility = VISIBLE
            btnTryAgain.visibility = VISIBLE
            tvNoConnectionMessage.text = "Error connecting to the server. Contact the admin or try again later"
            ivConnectionError.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.server_error))
        } else {
            ivConnectionError.visibility = visibility
            tvConnectionFailed.visibility = visibility
            tvNoConnectionMessage.visibility = visibility
            btnTryAgain.visibility = visibility
        }

    }

    private fun displayHomeViews(visibility: Int) {
        ivNewsFeatured.visibility = visibility
        tvLatestNews.visibility = visibility
        swipeToRefresh.visibility = visibility
    }

    private fun setupRecyclerView(){
        myAdapter = RecyclerViewAdapter { currentItem ->
            openWebPage(currentItem.url)
        }
        rvNews.adapter = myAdapter
        rvNews.layoutManager = LinearLayoutManager(this)
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}