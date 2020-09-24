package com.run.im.input.panel

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.animation.addListener
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
    var keyboardHeight: Int? = null
    var onStateNotify: ((animator: Animator?, listViewAnimate: Int) -> Unit)? = null

    override val containerView: View?
        get() = LayoutInflater.from(context).inflate(R.layout.layout_input_panel, this)


    init {
        containerView.hashCode()
        setClickListener()
    }


    private fun setClickListener() {
        audioImageView.setOnClickListener {
            onViewClick(isClickAudio = true, isClickEmoji = false, isClickSpecial = false, isClickEdt = false)
        }
        emotionImageView.setOnClickListener {
            onViewClick(isClickAudio = false, isClickEmoji = true, isClickSpecial = false, isClickEdt = false)
        }
        extImageView.setOnClickListener {
            onViewClick(isClickAudio = false, isClickEmoji = false, isClickSpecial = true, isClickEdt = false)
        }
        editText.setOnClickListener {
            onViewClick(isClickAudio = false, isClickEmoji = false, isClickSpecial = false, isClickEdt = true)
        }
    }

    private fun onViewClick(isClickAudio: Boolean, isClickEmoji: Boolean, isClickSpecial: Boolean, isClickEdt: Boolean) {
        if (isClickEdt) {
            if (isAudioShow) {
                throw IllegalStateException("00000000")
            }
            if (isEmojiShow) {
                emotionImageView.setImageResource(R.mipmap.ic_cheat_emo)
                //TODO 弹键盘&不做动画
                doAnimate(enlarge = 0, keyboardShow = 1)
                return
            }
            if (isSpecialShow) {
                //TODO 弹键盘&不做动画
                doAnimate(enlarge = 0, keyboardShow = 1)
                return
            }
            //TODO 弹键盘&放大动画
            doAnimate(enlarge = 1, keyboardShow = 1)
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
                //TODO 放大 键盘保持隐藏
                doAnimate(enlarge = 1, keyboardShow = 0)
                isAudioShow = false
                isSpecialShow = true
            }
            //这里就是默认状态 可能是默认初试状态那么需要弹键盘 也可能已经是放大状态了这个时候需要隐藏键盘显示special但是不做动画
            layMulti.layoutParams?.apply {
                specialLayout.visible()
                isSpecialShow = true
                if (height > 10) {
                    //TODO 不做动画!! 键盘收起来
                    doAnimate(enlarge = 0, keyboardShow = -1)
                    return@apply
                }
                //TODO 放大 键盘保持隐藏
                doAnimate(enlarge = 1, keyboardShow = 0)
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
                //TODO 弹键盘&放大
                doAnimate(enlarge = 1, keyboardShow = 1)
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
                //TODO 放大 键盘保持隐藏
                doAnimate(enlarge = 1, keyboardShow = 0)
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
                    //TODO 不做动画!! 收起来键盘
                    doAnimate(enlarge = 0, keyboardShow = -1)
                    return@apply
                }
                //TODO 放大动画 键盘保持隐藏
                doAnimate(enlarge = 1, keyboardShow = 0)

            }
            return
        }
        if (isClickAudio) {
            if (isAudioShow) {
                //先把语音部分干掉
                audioButton.invisible()
                audioImageView.setImageResource(R.mipmap.ic_cheat_voice)
                editText.visible()
                //TODO 弹键盘&放大
                doAnimate(enlarge = 1, keyboardShow = 1)
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
                //TODO 缩小 键盘保持隐藏
                doAnimate(enlarge = -1, keyboardShow = 0)
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
                //TODO 缩小 键盘保持隐藏
                doAnimate(enlarge = -1, keyboardShow = 0)
            }
            //这里就是默认状态 可能是默认初试状态那么需要弹键盘 也可能已经是放大状态了这个时候需要隐藏键盘显示emoji但是不做动画
            layMulti.layoutParams?.apply {
                audioButton.visible()
                editText.invisible()
                audioImageView.setImageResource(R.mipmap.ic_cheat_keyboard)
                isAudioShow = true
                if (height > 10) {
                    //TODO 缩小&收键盘
                    doAnimate(enlarge = -1, keyboardShow = -1)
                    return@apply
                }
                doAnimate(enlarge = 0, keyboardShow = 0)
            }
        }

    }


    /**
     * 配合键盘的弹起和回落
     * 输入框下方的view做放大or缩小动画
     * 0  保持原样
     * 1  放大or打开键盘
     * -1 缩小or关闭键盘
     */
    private fun doAnimate(enlarge: Int, keyboardShow: Int) {
        keyboardHeight ?: return
        if (enlarge == 0 && keyboardShow == 0) {//00
            onStateNotify?.invoke(null, -enlarge)
            return
        }
        if (enlarge == 0) {//01 0-1
            showKeyBoard(keyboardShow == 1)
            onStateNotify?.invoke(null, -enlarge)
            return
        }
        val animator = getAnimator(enlarge == 1, keyboardHeight!!)
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
        animator.addListener(onStart = {
            if (keyboardShow != 0) {
                showKeyBoard(keyboardShow == 1)
            }
        })
        onStateNotify?.invoke(animator, -enlarge)
    }


    private fun getAnimator(enlarge: Boolean, height: Int): ValueAnimator {
        val from = if (enlarge) 1 else height
        val to = if (enlarge) height else 1
        return ValueAnimator.ofInt(from, to)
    }

    private fun showKeyBoard(show: Boolean) {
        if (show) {
            editText.showKeyboard()
        } else {
            editText.hideKeyboard()
        }
    }


}