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
import com.example.mytopnews.R
import com.example.mytopnews.viewmodel.JokeViewModel

class JokeFragment : Fragment() {

    private lateinit var jokeViewModel: JokeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jokeViewModel =
            ViewModelProvider(this).get(JokeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_joke, container, false)

        val textView: TextView = root.findViewById(R.id.text_notifications)
        jokeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}