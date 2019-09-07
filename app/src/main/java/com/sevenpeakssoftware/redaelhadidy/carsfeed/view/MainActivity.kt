package com.sevenpeakssoftware.redaelhadidy.carsfeed.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.sevenpeakssoftware.redaelhadidy.carsfeed.R
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.addsTo
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.ApplicationModule
import com.sevenpeakssoftware.redaelhadidy.carsfeed.di.DaggerMainComponenet
import com.sevenpeakssoftware.redaelhadidy.carsfeed.presenter.ArticleListPresenter

import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_lyt.*
import java.lang.StringBuilder
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var presenter: ArticleListPresenter

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
            val stringBuilder = StringBuilder()
            it.forEach {
                stringBuilder.append(it.toString())
            }

            emptyTV.text = stringBuilder.toString()

        }.addsTo(compositeDisposable)

        presenter.errorBehaviourSubjectTrigger.observer().subscribe {
            error_lyt.visibility = View.VISIBLE
            errorTV.text = it.message
            retry.setOnClickListener { presenter.loadArticle() }

        }.addsTo(compositeDisposable)

        presenter.loadingBehaviourSubjectTrigger.observer().subscribe {
            it?.apply {
                if (this) {
                    error_lyt.visibility = View.GONE
                    loadingProgressBar.visibility = View.VISIBLE
                } else {
                    loadingProgressBar.visibility = View.GONE
                }
            }
        }.addsTo(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbound()
        compositeDisposable.clear()
    }
}


