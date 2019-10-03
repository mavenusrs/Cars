package com.sevenpeakssoftware.redaelhadidy.carsfeed.view

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenpeakssoftware.redaelhadidy.carsfeed.CarsApplication

import com.sevenpeakssoftware.redaelhadidy.carsfeed.R
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.DateHandler
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.addsTo
import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.ArticleContentParcelable
import com.sevenpeakssoftware.redaelhadidy.carsfeed.presenter.ArticleListPresenter
import com.sevenpeakssoftware.redaelhadidy.carsfeed.route.ARTICLE_DETAIL_EXTRA
import com.sevenpeakssoftware.redaelhadidy.carsfeed.route.Router
import com.sevenpeakssoftware.redaelhadidy.domain.errorchecker.ArticleException
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_lyt.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnItemClickListener, HasActivityInjector {

    @Inject
    lateinit var presenter: ArticleListPresenter
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    private lateinit var adapter: ArticleFeedAdapter
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injection()

        initRecyclerView()

        initErrorLayout()

        presenter.loadArticle()

        subscribeToPresenter()
    }

    private fun initErrorLayout() {
        retry.setOnClickListener { presenter.loadArticle() }
    }

    private fun injection() {
        (application as CarsApplication).initMainComponent().inject(this)
    }

    private fun subscribeToPresenter() {
        presenter.articlesBehaviourSubjectTrigger.observer().subscribe {
            it?.apply {
                if (it.isEmpty()) {
                    showEmptyMessage()
                } else {
                    showListOfArticle(it)
                }
            }
        }.addsTo(compositeDisposable)

        presenter.errorBehaviourSubjectTrigger.observer().subscribe {
            showError(getAppropriateMessage(this@MainActivity, it))

        }.addsTo(compositeDisposable)

        presenter.loadingBehaviourSubjectTrigger.observer().subscribe {
            it?.apply {
                showLoading(this)
            }
        }.addsTo(compositeDisposable)
    }

    private fun getAppropriateMessage(
        context: Context,
        articleException: ArticleException?
    ): String {
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

    private fun showLoading(show: Boolean) {
        toggleViewVisibility(showLoading = show)
    }

    private fun showError(errorMessage: String) {
        toggleViewVisibility(showError = true)
        errorTV.text = errorMessage
        error_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
    }

    private fun showEmptyMessage() {
        toggleViewVisibility(showEmpty = true)
        errorTV.text = getString(R.string.sorry_there_is_no_feeds)
        error_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_text_color))
    }

    private fun initRecyclerView() {
        adapter = ArticleFeedAdapter(null, this)
        articleRV.layoutManager = LinearLayoutManager(this)
        articleRV.adapter = adapter

        articleSRL.setOnRefreshListener {
            presenter.loadArticle()
            articleSRL.isRefreshing = true
        }

    }

    private fun showListOfArticle(articles: List<ArticleContentParcelable>) {
        toggleViewVisibility(showList = true)
        adapter.setItems(articles)
    }

    private fun toggleViewVisibility(
        showError: Boolean = false,
        showEmpty: Boolean = false,
        showList: Boolean = false,
        showLoading: Boolean = false
    ) {

        error_layout.visibility = if (showError || showEmpty) View.VISIBLE else View.GONE

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

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }
}


