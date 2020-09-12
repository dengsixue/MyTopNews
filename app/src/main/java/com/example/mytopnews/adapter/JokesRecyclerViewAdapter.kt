package com.example.mytopnews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytopnews.R
import com.example.mytopnews.model.data.Joke
import com.example.mytopnews.model.data.News
import com.example.mytopnews.util.ImageLoader

class JokesRecyclerViewAdapter:PagedListAdapter<Joke,JokesRecyclerViewAdapter.VH>(diffCallback) {
    lateinit var context: Context

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Joke>() {
            override fun areContentsTheSame(oldItem: Joke, jokeItem: Joke): Boolean =
                oldItem == jokeItem

            override fun areItemsTheSame(oldItem: Joke, jokeItem: Joke): Boolean =
                oldItem.hashId == jokeItem.hashId
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):VH {
        context=parent.context
        return VH.create(parent)
    }

    override fun onBindViewHolder(holder:VH, position: Int) {
        holder.bind(getItem(position)!!)
    }
    open class VH(val view: View) : RecyclerView.ViewHolder(view){
        private lateinit var  content: TextView
        private lateinit var upDateTime: TextView
        fun bind(joke: Joke){
            content=view.findViewById<TextView>(R.id.content)
            upDateTime= view.findViewById<TextView>(R.id.updatetime)
            joke.let {
                content.text=it.content
                upDateTime.text=it.updatetime
            }
        }

        companion object{
            fun create(parent: ViewGroup): VH {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.joke_item, parent, false)
                return VH(view)
            }
        }
    }
}