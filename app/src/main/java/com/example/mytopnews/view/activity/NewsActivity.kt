package com.example.mytopnews.view.activity

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mytopnews.R
import com.example.mytopnews.view.fragment.JokeFragment
import com.example.mytopnews.view.fragment.MineFragment
import com.example.mytopnews.view.fragment.NewsFragment
import com.example.mytopnews.view.fragment.WeatherFragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_news.*
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar.*

class NewsActivity : AppCompatActivity() {

    private var lastExitTime: Long = 0
    private var index = 0
    private var fragmentTag: String? = null
    private var mCurrentFragment: Fragment? = null
    private val fragmentNames = arrayOf(
        NewsFragment::class.java.name, WeatherFragment::class.java.name, JokeFragment::class.java.name,
        MineFragment::class.java.name
    )
    private val bottomTitles = arrayOf(R.string.news,R.string.weather,R.string.joke,R.string.mine)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar)
        initNavBottom()
        bottomNav()
        /**导航组件navigation，fragment切换时会重新创建fragment，会导致卡顿不流畅，所以舍弃**/
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            topLevelDestinationIds = setOf(
//                R.id.navigation_news, R.id.navigation_weather, R.id.navigation_joke
//                ,R.id.navigation_mine
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
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


    /**
     * onSupportNavigateUp()方法的重写，意味着Activity将它的 back键点击事件的委托出去，
     * 如果当前并非栈中顶部的Fragment,
     * 那么点击back键，返回上一个Fragment。
     *
     * **/
//    override fun onSupportNavigateUp() =
//        findNavController(R.id.nav_host_fragment).navigateUp()

    private fun initNavBottom(){
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val mOnNavigationItemSelectedListener =BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_news -> index = 0
                R.id.navigation_weather -> index = 1
                R.id.navigation_joke -> index = 2
                R.id.navigation_mine -> index = 3
            }
            bottomNav()
            true
        }

        navView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    private fun bottomNav() {
        toolbar.title = getString(
            if (index == 0) {
                R.string.app_name
            } else {
                bottomTitles[index]
            }
        )
        fragmentTag = fragmentNames[index]
        val fragment = getFragmentByTag(fragmentTag!!)
        showFragment(mCurrentFragment, fragment, fragmentTag!!)
    }
    private fun getFragmentByTag(name: String): Fragment {
        var fragment = supportFragmentManager.findFragmentByTag(name)
        if (fragment != null) {
            return fragment
        } else {
            try {
                fragment = Class.forName(name).newInstance() as Fragment
            } catch (e: Exception) {
                fragment = NewsFragment()
            }
        }
        return fragment!!
    }
    private fun showFragment(from: Fragment?, to: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        if (from == null) {
            if (to.isAdded) {
                transaction.show(to)
            } else {
                transaction.add(R.id.framelayout, to, tag)
            }
        } else {
            if (to.isAdded) {
                transaction.hide(from).show(to)
            } else {
                transaction.hide(from).add(R.id.framelayout, to, tag)
            }
        }
        transaction.commit()
        mCurrentFragment = to
    }
}
