package com.sevenpeakssoftware.redaelhadidy.carsfeed.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.sevenpeakssoftware.redaelhadidy.carsfeed.R
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.addsTo
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.ApplicationModule
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.DaggerMainComponenet
import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.ArticleContentParcelable
import com.sevenpeakssoftware.redaelhadidy.carsfeed.presenter.ArticleListPresenter
import com.sevenpeakssoftware.redaelhadidy.carsfeed.route.ARTICLE_DETAIL_EXTRA
import com.sevenpeakssoftware.redaelhadidy.carsfeed.route.Router
import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException

import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_lyt.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var presenter: ArticleListPresenter
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injection()

        subscribeToPresenter()

        presenter.loadArticle()

    }

    private fun injection() {
        DaggerMainComponenet.builder()
            .applicationModule(ApplicationModule(this))
            .build().jnject(this)
    }

    private fun subscribeToPresenter() {
        presenter.articlesBehaviourSubjectTrigger.observer().subscribe {
            it?.apply {
                if (it.isEmpty()){
                    showEmptyMessage()
                }else{
                    showListOfArticle(it)
                }
            }
        }.addsTo(compositeDisposable)

        presenter.errorBehaviourSubjectTrigger.observer().subscribe {
            showError(getAppropriateMessage(this@MainActivity, it))
            retry.setOnClickListener { presenter.loadArticle() }

        }.addsTo(compositeDisposable)

        presenter.loadingBehaviourSubjectTrigger.observer().subscribe {
            it?.apply {
                showLoading(this)
            }
        }.addsTo(compositeDisposable)
    }

    private fun getAppropriateMessage(
        context: Context,
        articleException: ArticleException?): String {
        var index = -1

        val errorKeys = context.resources.getStringArray(R.array.error_key)
        val errorValues = context.resources.getStringArray(R.array.error_value)

        for (key in errorKeys) {
            index++
            if (articleException?.errorCause == key.toInt())
                break
        }

        return if (index > 0 && index < errorValues.size)
            errorValues[index]
        else errorKeys[1]
    }

    fun showLoading(show: Boolean) {
        toggleViewVisibility(showLoading = show)
    }

    fun showError(errorMessage: String) {
        toggleViewVisibility(showError = true)
        errorTV.text = errorMessage
    }

    fun showEmptyMessage() {
        toggleViewVisibility(showEmpty = true)
    }

    fun showListOfArticle(articles: List<ArticleContentParcelable>) {
        toggleViewVisibility(showList = true)

        val adapter = ArticleFeedAdapter(articles, this)
        articleRV.layoutManager = LinearLayoutManager(this)
        articleRV.adapter = adapter

        articleSRL.setOnRefreshListener {
            presenter.loadArticle()
            articleSRL.isRefreshing = true
        }

    }

    private fun toggleViewVisibility(
        showError: Boolean = false,
        showEmpty: Boolean = false,
        showList: Boolean = false,
        showLoading: Boolean = false) {

        errorTV.visibility = if (showError) View.VISIBLE else View.GONE
        emptyTV.visibility = if (showEmpty) View.VISIBLE else View.GONE
        articleSRL.visibility = if (showList) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (showLoading) View.VISIBLE else View.GONE

        articleSRL.isRefreshing = false
    }

    override fun onItemClick(item: ArticleContentParcelable) {
        router.navigateTo(Router.ROUTE.ARTICLE_DETAILS, Bundle().apply {
            putParcelable(ARTICLE_DETAIL_EXTRA, item)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbound()
        compositeDisposable.clear()
    }
}


