package com.run.im.input.panel

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            showSpecial()
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
        if (isAudioShow) {
            hideAudio()
        }
        hideSpecial(clickAudio = false, clickEmoji = true)
        emotionImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
        isEmojiShow = true
        emotionLayout.visible()
        onAnimate(true)
    }

    private fun hideEmoji(clickAudio: Boolean = false, clickSpecial: Boolean = false, clickSelf: Boolean = false) {
        emotionLayout.gone()
        if (clickAudio) {
            onAnimate(false)
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
            onAnimate(false)
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
        editText.showKeyboard()
    }


    /**
     * 输入框下面的部分的高度动态调整
     */
    fun onAnimate(keyboardShow: Boolean?, height: Int? = com.run.im.input.keyboard.keyBoardHeight.value): ValueAnimator? {
        keyboardShow ?: return null
        height ?: return null
        val from = if (keyboardShow) 1 else height
        val to = if (keyboardShow) height else 1
        val animator: ValueAnimator = ValueAnimator.ofInt(from, to)
        animator.addUpdateListener {
            val heightAnimate = it.animatedValue as Int
            Log.e("onKeyboard--->", heightAnimate.toString())
            layMulti.layoutParams = layMulti.layoutParams?.apply {
                this.height = heightAnimate
            }
            layMulti.requestLayout()
            requestLayout()
            (parent as ViewGroup).requestLayout()
        }
        return animator
    }


}