package com.run.im.input

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View

fun String.isEnable(): Boolean {
    return this == "1"
}

fun String.visibility(): Int {
    return if (this.isEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun Boolean.visibility(): Int {
    return if (this) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


val String?.color: Int
    get() {
        if (this.isNullOrEmpty()) {
            return Color.TRANSPARENT
        }
        return Color.parseColor(this)
    }

val String?.int: Int
    get() {
        if (this.isNullOrEmpty()) {
            return 0
        }
        return this!!.toInt()
    }

/**
 *Convert int to dp
 */
val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Convert int to px
 */
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Convert float to dp
 */
val Float.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Convert float to px
 */
val Float.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


/**
 * 获取当前屏幕显示区域的宽度
 */
fun Context.displayScreenWidth(): Int {
    return getDisplayMetrics().widthPixels
}

/**
 * 获取当前屏幕显示区域的高度, 包括statusbar, 不包括navigation bar
 */
fun Context.displayScreenHeight(): Int {
    return getDisplayMetrics().heightPixels
}


private fun Context.getDisplayMetrics(): DisplayMetrics {
    when {
        this is Application -> return this.resources.displayMetrics
        this is Activity -> {
            val windowManager = this.windowManager
            val d = windowManager.defaultDisplay

            val displayMetrics = DisplayMetrics()
            d.getMetrics(displayMetrics)
            return displayMetrics
        }

    }
    return DisplayMetrics()
}

/**
 * 获取整个屏幕的宽度
 */
fun Context.realScreenWidth(): Int {
    return getRealMetrics().widthPixels
}

/**
 * 获取整个屏幕的高度, 包含statusbar, 包含navigation bar
 */
fun Context.realScreenHeight(): Int {
    return getRealMetrics().heightPixels
}

fun Context.isFullScreenMobile(): Boolean {
    return screenHeight() > 1920
}

private fun Context.getRealMetrics(): DisplayMetrics {
    this as Activity
    val windowManager = this.windowManager
    val d = windowManager.defaultDisplay

    val realDisplayMetrics = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        d.getRealMetrics(realDisplayMetrics)
    }
    return realDisplayMetrics
}


/**
 * 获取屏幕宽度
 */
fun Context.screenWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        if (this is Activity) {
            val outMetrics = DisplayMetrics()
            this.windowManager.defaultDisplay.getRealMetrics(outMetrics)
            outMetrics.widthPixels
        } else {
            val displayMetrics = this.resources.displayMetrics
            displayMetrics.heightPixels
        }
    } else {
        val displayMetrics = this.resources.displayMetrics
        displayMetrics.heightPixels
    }
}

/**
 * 获取屏幕高度
 */
fun Context.screenHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        if (this is Activity) {
            val outMetrics = DisplayMetrics()
            this.windowManager.defaultDisplay.getRealMetrics(outMetrics)
            outMetrics.heightPixels
        } else {
            val displayMetrics = this.resources.displayMetrics
            displayMetrics.heightPixels
        }
    } else {
        val displayMetrics = this.resources.displayMetrics
        displayMetrics.heightPixels
    }
}

/**
 * 获取状态栏的高度
 */
fun Context.statusBarHeight(): Int {
    var height = 0
    val resId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resId > 0) {
        height = this.resources.getDimensionPixelSize(resId)
    }
    return height
}

fun View.flipVisibility(invisible: Int = View.GONE): Boolean {
    if (visibility == View.VISIBLE) {
        visibility = invisible
        return false
    }
    visibility = View.VISIBLE
    return true

}

fun View?.visible() {
    this?.let {
        visibility = View.VISIBLE
    }
}

fun View?.invisible() {
    this?.let {
        visibility = View.INVISIBLE
    }
}

fun View?.gone() {
    this?.let {
        visibility = View.GONE
    }
}


















