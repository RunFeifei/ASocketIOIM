package com.run.im.input

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import java.io.InputStream

/**
 * Created by PengFeifei on 2020/9/1.
 */

fun getBitmapFromAsset(context: Context?, assetPath: String?): BitmapDrawable? {
    context ?: return null
    if (assetPath.isNullOrEmpty()) {
        return null
    }
    val resources = context.resources
    val options = BitmapFactory.Options()
    options.inDensity = DisplayMetrics.DENSITY_HIGH
    options.inScreenDensity = resources.displayMetrics.densityDpi
    options.inTargetDensity = resources.displayMetrics.densityDpi

    var inputStream: InputStream? = null
    return try {
        inputStream = context.assets.open(assetPath)
        val bitmap = BitmapFactory.decodeStream(inputStream, Rect(), options)
        BitmapDrawable(resources, bitmap)
    } catch (ignore: Exception) {
        ignore.printStackTrace()
        null
    } finally {
        inputStream?.let {
            try {
                it.close()
            } catch (ignore: Exception) {
                ignore.printStackTrace()
            }
        }
    }

}