package com.example.mytopnews.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.example.mytopnews.R
import com.example.mytopnews.adapter.NewsTypeFragmentPagesAdapter
import com.example.mytopnews.model.remote.RemoteData
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_CAIJING
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_GUOJI
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_GUONEI
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_JUNSH
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_KEJI
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_SHEHUI
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_SHISHANG
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_TIYU
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_YULE
import com.example.mytopnews.model.remote.RemoteData.NEW_TYPE_TOP
import com.example.mytopnews.viewmodel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var typeList: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val array:Array<String> = arrayOf(
            NEW_TYPE_TOP
        ,NEWS_TYPE_SHEHUI
        ,NEWS_TYPE_GUONEI
        ,NEWS_TYPE_GUOJI
        ,NEWS_TYPE_YULE
        , NEWS_TYPE_TIYU
        ,NEWS_TYPE_JUNSH
        ,NEWS_TYPE_KEJI
        ,NEWS_TYPE_CAIJING
        ,NEWS_TYPE_SHISHANG)
        typeList= activity?.resources?.getStringArray(R.array.types) as Array<String>

//        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_news, container, false)
        val mPager=root.findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout=root.findViewById<TabLayout>(R.id.type_table)
        mPager?.adapter=NewsTypeFragmentPagesAdapter(this,array)
//        mPager.offscreenPageLimit=2
        TabLayoutMediator(tabLayout,mPager){tab, position ->
            tab.text=typeList[position]
        }.attach()

//        tabLayout.run {
//            setupWithViewPager2(mPager)
//            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//                override fun onTabReselected(tab: TabLayout.Tab?) {
//                }
//
//                override fun onTabUnselected(tab: TabLayout.Tab?) {
//                }
//
//                override fun onTabSelected(tab: TabLayout.Tab?) {
//                    tab?.let {
//                        mPager.setCurrentItem(it.position, false)
//                    }
//                }
//            })
//        }


        return root
    }


}