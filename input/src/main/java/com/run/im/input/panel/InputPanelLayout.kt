package com.run.im.input.panel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.run.im.input.R

/**
 * Created by PengFeifei on 2020/9/03.
 */
class InputPanelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_input_panel, this)
    }

}