package com.run.im.input.panel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.run.im.input.R
import com.run.im.input.gone
import com.run.im.input.keyboard.hideKeyboard
import com.run.im.input.keyboard.showKeyboard
import com.run.im.input.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_input_panel.*

/**
 * Created by PengFeifei on 2020/9/03.
 */
class InputPanelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle), LayoutContainer {

    private var isAudioShow = false
    private var isEmojiShow = false
    private var isSpecialShow = false

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
            doKeyboard()
        }
        emotionImageView.setOnClickListener {
            if (isEmojiShow) {
                hideEmoji(clickAudio = false, clickSpecial = false, clickSelf = true)
            } else {
                showEmoji()
            }
            doKeyboard()
        }
        extImageView.setOnClickListener {
            if (isSpecialShow) {
                hideSpecial(clickAudio = false, clickEmoji = false, clickSelf = true)
            } else {
                showSpecial()
            }
            doKeyboard()
        }
        editText.setOnClickListener {
            editText.showKeyboard()
        }
    }


    private fun showAudio() {
        audioButton.visible()
        hideEdit()
        audioImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
        hideEmoji(clickAudio = true, clickSpecial = false)
        hideSpecial(clickAudio = true, clickEmoji = false)
        isAudioShow = true
    }

    private fun hideAudio() {
        audioButton.gone()
        showEdit()
        audioImageView.setImageResource(R.mipmap.ic_cheat_voice)
        isAudioShow = false
    }

    private fun showEmoji() {
        hideAudio()
        hideSpecial(clickAudio = false, clickEmoji = true)
        emotionImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
        isEmojiShow = true
        layMulti.visible()
        emotionLayout.visible()
    }

    private fun hideEmoji(clickAudio: Boolean = false, clickSpecial: Boolean = false, clickSelf: Boolean = false) {
        emotionLayout.gone()
        if (clickAudio) {
            layMulti.gone()
        }
        if (clickSelf) {
            editText.showKeyboard()
        }
        emotionImageView.setImageResource(R.mipmap.ic_cheat_emo)
        isEmojiShow = false
    }

    private fun showSpecial() {
        specialLayout.visible()
        hideEmoji(clickAudio = false, clickSpecial = true)
        hideAudio()
        isSpecialShow = true
    }

    private fun hideSpecial(clickAudio: Boolean = false, clickEmoji: Boolean = false, clickSelf: Boolean = false) {
        specialLayout.gone()
        if (clickAudio) {
            layMulti.gone()
        }
        if (clickSelf) {
            editText.showKeyboard()
        }
        isSpecialShow = false
    }

    private fun showEdit() {
        editText.visible()
    }

    private fun hideEdit() {
        editText.gone()
    }

    private fun doKeyboard() {
        if (isAudioShow || isEmojiShow || isSpecialShow) {
            editText.hideKeyboard()
            return
        }
    }

    fun onKeyboard(show: Boolean?, keyboardHeight: Int?) {
        show ?: return
        keyboardHeight ?: return
        post {
            if (!show && !isSpecialShow && !isEmojiShow) {
                layMulti.gone()
            } else {
                layMulti.layoutParams?.apply {
                    if (height == keyboardHeight) {
                        return@apply
                    }
                    height = keyboardHeight
                    layMulti.layoutParams = this
                }
                layMulti.visible()
            }
        }

    }


}