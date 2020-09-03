package com.run.im.input.panel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.run.im.input.R
import com.run.im.input.flipVisibility
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_input_panel.*

/**
 * Created by PengFeifei on 2020/9/03.
 */
class InputPanelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle), LayoutContainer {

    override val containerView: View?
        get() = LayoutInflater.from(context).inflate(R.layout.layout_input_panel, this)

    init {
        containerView.hashCode()
        setClickListener()
    }


    private fun setClickListener() {
        audioImageView.setOnClickListener {
            doAudioRecordingTransfer()
        }
        emotionImageView.setOnClickListener {
            doEmotionSelect()
        }
    }

    private fun doAudioRecordingTransfer() {
        audioButton.flipVisibility()
        editText.flipVisibility(INVISIBLE).let {
            audioImageView.setImageResource(if (it) R.mipmap.ic_cheat_voice else R.mipmap.ic_cheat_keyboard)
        }
    }

    private fun doEmotionSelect() {
        emotionLayout.flipVisibility()
    }


}