package com.example.mytopnews.view.fragment

import android.os.Bundle
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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mytopnews.R
import com.example.mytopnews.adapter.ItemRecyclerViewAdapter
import com.example.mytopnews.adapter.JokesRecyclerViewAdapter
import com.example.mytopnews.model.data.NetworkState
import com.example.mytopnews.model.data.repository.JokesRepository
import com.example.mytopnews.model.data.repository.NewsRepository
import com.example.mytopnews.viewmodel.JokeViewModel
import com.example.mytopnews.viewmodel.TopFragmentViewModel

class JokeFragment : Fragment() {

    private lateinit var jokeViewModel: JokeViewModel
    private lateinit var rv: RecyclerView
    private lateinit var linearLayout: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var retryButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root=inflater.inflate(R.layout.fragment_type_base,container,false)
        jokeViewModel = ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return JokeViewModel(JokesRepository.getInstance(),"1598000217") as T
            }
        }).get(JokeViewModel::class.java)
        val adapter = JokesRecyclerViewAdapter()
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
            jokeViewModel.refresh()

        }
        retryButton.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            swipeRefresh.isRefreshing=false
            jokeViewModel.retry()
        }
        //初始化数据
        jokeViewModel.newsList.observe(viewLifecycleOwner, Observer(adapter::submitList))
        //数据加载状态观察者
        jokeViewModel.refreshState.observe(viewLifecycleOwner, Observer{
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
        return root
    }
}