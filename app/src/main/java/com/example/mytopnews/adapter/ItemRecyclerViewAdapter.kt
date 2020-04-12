package com.example.mytopnews.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytopnews.R
import com.example.mytopnews.model.data.News
import com.example.mytopnews.util.Constants
import com.example.mytopnews.util.ImageLoader
import com.example.mytopnews.view.activity.WebActivity
import org.jetbrains.anko.image

class ItemRecyclerViewAdapter() : PagedListAdapter<News,ItemRecyclerViewAdapter.VH>(diffCallback) {
    private val TYPE_ONEIMAGEITEM = 0
    private val TYPE_THREEIMAGEITEM = 1
    lateinit var context: Context

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem.uniquekey == newItem.uniquekey
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position)?.thumbnail_pic_s02 != null && getItem(position)?.thumbnail_pic_s03 != null) {
            return TYPE_THREEIMAGEITEM
        } else {
            return TYPE_ONEIMAGEITEM
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        context=parent.context
        return when (viewType) {
            TYPE_ONEIMAGEITEM -> VH.create(parent)
            TYPE_THREEIMAGEITEM -> ImagesVh.create(parent)
            else -> throw IllegalArgumentException("未知 view type = $viewType")
        }
    }

    private fun setOnClick(holder:RecyclerView.ViewHolder,news: News){
        holder.itemView.setOnClickListener{v: View? ->
            Intent(context, WebActivity::class.java).run {
                putExtra(Constants.WEB_TITLE, news.title)
                putExtra(Constants.WEB_URL, news.url)
                context.startActivity(this)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        when(getItemViewType(position)){
            TYPE_ONEIMAGEITEM -> holder.bind(getItem(position)!!)
            TYPE_THREEIMAGEITEM -> (holder as ImagesVh).bindto(getItem(position)!!)
        }
        setOnClick(holder,getItem(position)!!)
//        val news = getItem(position)
//        holder.bind(news!!)

    }
    open class VH(val view: View) :RecyclerView.ViewHolder(view){
        private lateinit var  title:TextView
        private lateinit var source:TextView
        private lateinit var image:ImageView
        private lateinit var date:TextView
        fun bind(news: News){
            title= view.findViewById<TextView>(R.id.title)
            source=view.findViewById<TextView>(R.id.source)
            image=view.findViewById<ImageView>(R.id.news_image)
            date=view.findViewById<TextView>(R.id.date)
            news.let {
                title.text= it.title
                source.text=it.author_name
                date.text=it.date
                ImageLoader.load(view.context,news.thumbnail_pic_s,image)
            }
        }

        companion object{
            fun create(parent: ViewGroup): VH {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item, parent, false)
                return VH(view)
            }
        }
    }

    class ImagesVh(itemView:View) :VH(itemView){
        private lateinit var  imagesTitle:TextView
        private lateinit var imagesSource:TextView
        private lateinit var image1:ImageView
        private lateinit var image2:ImageView
        private lateinit var image3:ImageView
        private lateinit var imagesDate:TextView
        fun bindto(news: News){
            imagesTitle= itemView.findViewById(R.id.images_title)
            imagesSource=itemView.findViewById(R.id.images_source)
            image1=itemView.findViewById(R.id.image_1)
            image2=itemView.findViewById(R.id.image_2)
            image3=itemView.findViewById(R.id.image_3)
            imagesDate=itemView.findViewById(R.id.images_date)
            news.let {
                imagesTitle.text= it.title
                imagesSource.text=it.author_name
                imagesDate.text=it.date
                ImageLoader.load(itemView.context,news.thumbnail_pic_s,image1)
                ImageLoader.load(itemView.context,news.thumbnail_pic_s02,image2)
                ImageLoader.load(itemView.context,news.thumbnail_pic_s03,image3)
            }

        }
        companion object{
            fun create(parent: ViewGroup): ImagesVh {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item_3image, parent, false)
                return ImagesVh(view)
            }
        }
    }

}