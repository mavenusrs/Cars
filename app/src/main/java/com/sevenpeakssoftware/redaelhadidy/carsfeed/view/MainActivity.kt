package com.sevenpeakssoftware.redaelhadidy.carsfeed.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.sevenpeakssoftware.redaelhadidy.carsfeed.R
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.addsTo
import com.sevenpeakssoftware.redaelhadidy.carsfeed.presenter.ArticleListPresenter
import com.sevenpeakssoftware.redaelhadidy.data.BASE_URL
import com.sevenpeakssoftware.redaelhadidy.data.DATABASE_NAME
import com.sevenpeakssoftware.redaelhadidy.data.common.DeviceHandler
import com.sevenpeakssoftware.redaelhadidy.data.local.ArticleFeedDatabase
import com.sevenpeakssoftware.redaelhadidy.data.local.SharedPreferenceHandler
import com.sevenpeakssoftware.redaelhadidy.data.remote.ArticleFeedApi
import com.sevenpeakssoftware.redaelhadidy.data.repository.ArticleRepositoryImpl
import com.sevenpeakssoftware.redaelhadidy.data.repository.SynchronizationRepositoryImpl
import com.sevenpeakssoftware.redaelhadidy.domain.sync.SynchronizationEngine
import com.sevenpeakssoftware.redaelhadidy.domain.usecase.GetArticleUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_lyt.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var presenter: ArticleListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = provideRetrofit()

        val database = provideDatabase()

        val synchronizationRepository = SynchronizationRepositoryImpl(
            SharedPreferenceHandler(this),
            DeviceHandler(this)
        )

        presenter = ArticleListPresenter(
            GetArticleUseCase(
                ArticleRepositoryImpl(
                    retrofit.create(ArticleFeedApi::class.java),
                    database.articleDAO()
                ), SynchronizationEngine(synchronizationRepository)
            ), Schedulers.io(), AndroidSchedulers.mainThread()
        )

        subscribeToPresenter()

        presenter.loadArticle()
    }

    private fun subscribeToPresenter() {
        presenter.articlesBehaviourSubjectTrigger.observer().subscribe {
            val stringBuilder = StringBuilder()
            it.forEach {
                stringBuilder.append(it.toString())
            }


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
//            Toast.makeText(this@MainActivity, "loading", Toast.LENGTH_SHORT).show()
        }.addsTo(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbound()
        compositeDisposable.clear()
    }


    fun provideRetrofit(): Retrofit {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(logging)

        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
    }

    fun provideDatabase(): ArticleFeedDatabase {
        return Room.databaseBuilder(this, ArticleFeedDatabase::class.java, DATABASE_NAME).build()
    }
}
