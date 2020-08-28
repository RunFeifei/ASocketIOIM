package com.run.im.input

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by PengFeifei on 2020/8/28.
 */
class EmotionLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int, override val containerView: View?) : FrameLayout(context, attrs, defStyle), LayoutContainer {


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_emotion, this)
    }

}
