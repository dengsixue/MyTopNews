package com.example.mytopnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.example.mytopnews.R
import com.example.mytopnews.view.fragment.newstypefragment.TopFragment
import kotlin.reflect.typeOf

class NewsTypeFragmentPagesAdapter(fm:FragmentManager, private val types:Array<String>,val titles:Array<String>) :FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return TopFragment(position,types[position])
    }

    override fun getCount(): Int {
        return types.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

//    override fun getItemCount(): Int {
//        return types.size
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        return TopFragment(position,types[position])
//    }

}
