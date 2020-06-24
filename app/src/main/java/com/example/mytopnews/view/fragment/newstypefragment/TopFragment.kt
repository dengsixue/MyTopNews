package com.example.mytopnews.view.fragment.newstypefragment

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mytopnews.R
import com.example.mytopnews.adapter.ItemRecyclerViewAdapter
import com.example.mytopnews.adapter.NewsTypeFragmentPagesAdapter
import com.example.mytopnews.model.data.Data
import com.example.mytopnews.model.data.HttpResponse
import com.example.mytopnews.model.data.NetworkState
import com.example.mytopnews.model.data.repository.NewsRepository
import com.example.mytopnews.model.remote.HttpClient
import com.example.mytopnews.model.remote.RemoteData
import com.example.mytopnews.view.fragment.NewsTypeBaseFragment
//import com.example.mytopnews.viewmodel.NewsListViewModel
import com.example.mytopnews.viewmodel.NewsViewModel
import com.example.mytopnews.viewmodel.TopFragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.container.*
import kotlinx.android.synthetic.main.container.container
import kotlinx.android.synthetic.main.fragment_type_base.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.support.v4.act

@Suppress("UNCHECKED_CAST")
class TopFragment(private var i:Int,val types:String) :Fragment() {
    private lateinit var newsListViewModel: TopFragmentViewModel
    private lateinit var rv: RecyclerView
    private lateinit var linearLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var retryButton: Button


    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.fragment_type_base,container,false)
        //获取ViewModel
        newsListViewModel = ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TopFragmentViewModel(NewsRepository.getInstance(),types) as T
            }
        }).get(TopFragmentViewModel::class.java)
        val adapter = ItemRecyclerViewAdapter()
        root.run {
            rv=findViewById(R.id.list_item)
            linearLayout=findViewById(R.id.error_msg)
            progressBar=findViewById(R.id.progressBar)
            swipeRefresh=findViewById(R.id.swipeRefreshLayout)
            retryButton=findViewById(R.id.retry_button)
        }
        rv.adapter=adapter

        progressBar.visibility=View.VISIBLE
        //下拉刷新
        swipeRefresh.setColorSchemeResources(R.color.appColor)
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing=true
            newsListViewModel.refresh()

        }
        retryButton.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            swipeRefresh.isRefreshing=false
            newsListViewModel.retry()
        }
        //初始化数据
        newsListViewModel.newsList.observe(viewLifecycleOwner, Observer(adapter::submitList))
        //数据加载状态观察者
        newsListViewModel.refreshState.observe(viewLifecycleOwner, Observer{
            when(it){
                NetworkState.LOADING->{
                    linearLayout.visibility=View.INVISIBLE
//                    progressBar.visibility=View.VISIBLE
                    rv.visibility=View.INVISIBLE
                }
                NetworkState.HIDDEN->{
                    linearLayout.visibility=View.INVISIBLE
//                    progressBar.visibility=View.VISIBLE
                    rv.visibility=View.INVISIBLE
                }
                NetworkState.LOADED-> {
                    linearLayout.visibility=View.INVISIBLE
                    rv.visibility=View.VISIBLE
                    progressBar.visibility=View.INVISIBLE
                    swipeRefresh.isRefreshing=false
                }
                NetworkState.FAILED->{
                    rv.visibility=View.INVISIBLE
                    linearLayout.visibility=View.VISIBLE
                    progressBar.visibility=View.INVISIBLE
                    swipeRefresh.isRefreshing=false
                }
            }
        }
        )
//        val s= HttpClient.getInstance().ApiLinkService.getNcovData()
        return root
    }
}