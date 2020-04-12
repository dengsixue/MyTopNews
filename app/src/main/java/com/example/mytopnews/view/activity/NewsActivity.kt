package com.example.mytopnews.view.activity

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mytopnews.R
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class NewsActivity : AppCompatActivity() {

    private var lastExitTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.navigation_news, R.id.navigation_weather, R.id.navigation_joke
                ,R.id.navigation_mine
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        navView.setupWithNavController(navController)
    }

    @SuppressLint("ShowToast")
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastExitTime > 1500) {
                Toast.makeText(applicationContext,"双击退出",Toast.LENGTH_LONG).show()
//                ToastUtils.show(getString(R.string.exit_hint))
                lastExitTime = currentTime
                return true
            } else {
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
