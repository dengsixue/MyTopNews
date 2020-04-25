package com.example.mytopnews.view.activity

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.mytopnews.R
import com.example.mytopnews.util.Constants
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

@SuppressLint("Registered")
open class WebBaseActivity : SwipeBackActivity() {
    private var mSwipeBackLayout: SwipeBackLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true)
        mSwipeBackLayout = swipeBackLayout
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM

        mSwipeBackLayout?.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)

        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
//        val resources:Resources=this.resources
//        val dm:DisplayMetrics=resources.displayMetrics
//        mSwipeBackLayout?.setEdgeSize(dm.widthPixels/2)
    }

}