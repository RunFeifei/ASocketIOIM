package com.run.im.input.panel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.run.im.input.R
import com.run.im.input.gone
import com.run.im.input.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_input_panel.*

/**
 * Created by PengFeifei on 2020/9/03.
 */
class InputPanelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle), LayoutContainer {

    private var isAudioShow = false
    private var isEmojiShow = false

    override val containerView: View?
        get() = LayoutInflater.from(context).inflate(R.layout.layout_input_panel, this)


    init {
        containerView.hashCode()
        setClickListener()
    }


    private fun setClickListener() {
        audioImageView.setOnClickListener {
            if (isAudioShow) {
                hideAudio()
            } else {
                showAudio()
            }
        }
        emotionImageView.setOnClickListener {
            if (isEmojiShow) {
                hideEmoji()
            } else {
                showEmoji()
            }
        }
    }


    private fun showAudio() {
        audioButton.visible()
        editText.gone()
        audioImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
        hideEmoji()
        isAudioShow = true
    }

    private fun hideAudio() {
        audioButton.gone()
        editText.visible()
        audioImageView.setImageResource(R.mipmap.ic_cheat_voice)
        isAudioShow = false
    }

    private fun showEmoji() {
        emotionLayout.visible()
        hideAudio()
        emotionImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
        isEmojiShow = true
    }

    private fun hideEmoji() {
        emotionLayout.gone()
        emotionImageView.setImageResource(R.mipmap.ic_cheat_emo)
        isEmojiShow = false
    }


}