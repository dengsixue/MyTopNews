package com.example.mytopnews.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.example.mytopnews.R
import com.example.mytopnews.viewmodel.WeatherViewModel

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mLocationOption =AMapLocationClientOption()
        val mlocationClient=AMapLocationClient(context)
        mlocationClient.setLocationOption(mLocationOption)
        mlocationClient.startLocation()
        mlocationClient.setLocationListener {  }
            weatherViewModel =
            ViewModelProvider(this).get(WeatherViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_weather, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        weatherViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}