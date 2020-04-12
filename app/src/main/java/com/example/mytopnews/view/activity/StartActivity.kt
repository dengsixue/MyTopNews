package com.example.mytopnews.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytopnews.R
import com.example.mytopnews.view.activity.NewsActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class StartActivity : AppCompatActivity() {

    var mDisposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        mDisposable=Observable.interval(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t: Long? ->
                    startActivity<NewsActivity>()
                    this.finish()
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable?.dispose()
    }
}
