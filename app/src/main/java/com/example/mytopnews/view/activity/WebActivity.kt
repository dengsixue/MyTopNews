package com.example.mytopnews.view.activity

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.mytopnews.R
import com.example.mytopnews.util.Constants
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.NestedScrollAgentWebView
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : WebBaseActivity() {
    private var agentWeb: AgentWeb? = null
    private lateinit var webTitle: String
    private lateinit var webUrl: String
    private lateinit var errorMsg: TextView
    private val mWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        intent.extras?.run {
            webUrl = getString(Constants.WEB_URL, "")
        }
        initWebView()
    }
    private fun initWebView(){
        val layoutParams = CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val errorView = layoutInflater.inflate(R.layout.web_error_page,null)
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(web_container,ViewGroup.LayoutParams(-1,-1))
            .useDefaultIndicator()
            .setWebView(mWebView)
            .setWebChromeClient(WebChromeClient())
            .setMainFrameErrorView(errorView)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .ready()
            .go(webUrl)
        agentWeb?.webCreator?.webView?.run {
            settings.domStorageEnabled = true
//            webViewClient = WebViewClient()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }
    override fun onBackPressed() {
        agentWeb?.run {
            if (!back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }
    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}
