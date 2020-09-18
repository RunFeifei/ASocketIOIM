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
import com.run.im.input.invisible
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
            notifyState(isClickAudio = true, isClickEmoji = false, isClickSpecial = false, isClickEdt = false)
        }
        emotionImageView.setOnClickListener {
            notifyState(isClickAudio = false, isClickEmoji = true, isClickSpecial = false, isClickEdt = false)
        }
        extImageView.setOnClickListener {
            notifyState(isClickAudio = false, isClickEmoji = false, isClickSpecial = true, isClickEdt = false)
        }
        editText.setOnClickListener {
            notifyState(isClickAudio = false, isClickEmoji = false, isClickSpecial = false, isClickEdt = true)
        }
    }

    private fun notifyState(isClickAudio: Boolean, isClickEmoji: Boolean, isClickSpecial: Boolean, isClickEdt: Boolean) {
        if (isClickEdt) {
            if (isAudioShow) {
                throw IllegalStateException("00000000")
            }
            if (isEmojiShow) {
                emotionImageView.setImageResource(R.mipmap.ic_cheat_emo)
                editText.showKeyboard()
            }
            if (isSpecialShow) {
                editText.showKeyboard()
            }
            return
        }
        //点击了special
        if (isClickSpecial) {
            //如果已经在显示中了
            if (isSpecialShow) {
                return
            }
            if (isEmojiShow && isAudioShow) {
                throw IllegalStateException("111111")
            }
            //如果显示的是emoji,此时isAudioShow一定是false
            if (isEmojiShow) {
                emotionLayout.invisible()
                specialLayout.visible()
                isEmojiShow = false
                isSpecialShow = true
                return
            }
            //如果显示的是语音,那么此时一定isEmojiShow=false
            if (isAudioShow) {
                //先把语音部分干掉
                audioButton.invisible()
                audioImageView.setImageResource(R.mipmap.ic_cheat_voice)
                editText.visible()

                specialLayout.visible()
                //TODO 做放大动画
                doAnimate(enlarge = true, doNothing = false)
                isAudioShow = false
                isSpecialShow = true
            }
            //这里就是默认状态 可能是默认初试状态那么需要弹键盘 也可能已经是放大状态了这个时候需要隐藏键盘显示special但是不做动画
            layMulti.layoutParams?.apply {
                specialLayout.visible()
                isSpecialShow = true
                if (height > 10) {
                    editText.hideKeyboard()
                    //TODO 不做动画!!
                    return@apply
                }
                //TODO 做放大动画
            }

            return
        }
        //点击了emoji
        if (isClickEmoji) {
            //如果已经在显示中了,那么需要切回到默认状态
            if (isEmojiShow) {
                //这个应该是多余的
                editText.visible()
                emotionImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
                emotionLayout.invisible()
                editText.showKeyboard()
                isEmojiShow = false
                return
            }
            if (isSpecialShow && isAudioShow) {
                throw IllegalStateException("22222222")
            }
            //下面的逻辑 emoji就是没有显示中了
            if (isAudioShow) {
                //先把语音部分干掉
                audioButton.invisible()
                audioImageView.setImageResource(R.mipmap.ic_cheat_voice)
                editText.visible()
                emotionLayout.visible()
                emotionImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
                //TODO 做放大动画
                isAudioShow = false
                isEmojiShow = true
                return
            }
            if (isSpecialShow) {
                specialLayout.invisible()
                emotionLayout.visible()
                emotionImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
                isSpecialShow = false
                isEmojiShow = true
                return
            }
            //这里就是默认状态 可能是默认初试状态那么需要弹键盘 也可能已经是放大状态了这个时候需要隐藏键盘显示emoji但是不做动画
            layMulti.layoutParams?.apply {
                emotionLayout.visible()
                emotionImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
                isEmojiShow = true
                if (height > 10) {
                    editText.hideKeyboard()
                    //TODO 不做动画!!
                    return@apply
                }
                //TODO 放大动画
            }
            return
        }
        if (isClickAudio) {
            if (isAudioShow) {
                //先把语音部分干掉
                audioButton.invisible()
                audioImageView.setImageResource(R.mipmap.ic_cheat_voice)
                editText.visible()
                editText.showKeyboard()
                isAudioShow = false
                return
            }
            if (isSpecialShow && isEmojiShow) {
                throw IllegalStateException("333333333")
            }
            if (isSpecialShow) {
                specialLayout.invisible()
                //语音显示出来
                audioButton.visible()
                editText.invisible()
                audioImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
                isSpecialShow = false
                isAudioShow = true
                //TODO 做缩小动画!!
                return
            }
            if (isEmojiShow) {
                emotionLayout.invisible()
                emotionImageView.setImageResource(R.mipmap.ic_cheat_emo)
                //语音显示出来
                audioButton.visible()
                editText.invisible()
                audioImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
                isEmojiShow = false
                isAudioShow = true
                //TODO 做缩小动画!!
            }
            //这里就是默认状态 可能是默认初试状态那么需要弹键盘 也可能已经是放大状态了这个时候需要隐藏键盘显示emoji但是不做动画
            layMulti.layoutParams?.apply {
                audioButton.visible()
                editText.invisible()
                audioImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
                isAudioShow = true
                if (height > 10) {
                    editText.hideKeyboard()
                    return@apply
                }
            }
        }

    }


    /**
     * 配合键盘的弹起和回落
     * 输入框下方的view做放大or缩小动画
     */
    private fun doAnimate(enlarge: Boolean, doNothing: Boolean) {

    }


    /**
     * 输入框下面的部分的高度动态调整
     * 禁止内部调用!!!!!!!
     * 1 如果显示表情中 点击输入框 不需要再做动画了!!! 只需要弹键盘
     * 2 special显示中 但是没有键盘 点击了语音.不需要隐藏键盘 但是需要做回缩动画
     * 3 special显示中 但是没有键盘 点击了输入框,只需要弹键盘不需要做动画
     */
    fun getAnimate(keyboardShow: Boolean?, height: Int? = com.run.im.input.keyboard.keyBoardHeight.value): ValueAnimator? {
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

    fun showKeyBoard(show: Boolean) {

    }


}