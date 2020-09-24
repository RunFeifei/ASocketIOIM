package com.run.im

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.run.im.input.keyboard.AdjustNothingKeyboardStateHelper
import com.run.im.input.keyboard.keyBoardHeight
import com.run.im.input.keyboard.keyBoardState
import com.run.im.input.panel.InputPanelLayout

/**
 * Created by PengFeifei on 2020/9/7.
 */
class IMLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    var listView: RecyclerView
    var inputView: InputPanelLayout
    private var owner: LifecycleOwner? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_msglist_input, this)
        listView = findViewById(R.id.listView)
        inputView = findViewById(R.id.inputView)
        inputView.onStateNotify = object : ((Animator?, Int) -> Unit) {
            override fun invoke(p1: Animator?, p2: Int) {
                listViewMove(p1, p2)
            }
        }
    }

    fun setActivity(owner: LifecycleOwner?) {
        owner?.let { own ->
            this.owner = own
            AdjustNothingKeyboardStateHelper(own).nada()
            keyBoardState.observe(own, Observer<Boolean?> {
                it?.apply {
                    showToast(if (this) "open" else "close")
                }
            })

            keyBoardHeight.observe(own, Observer<Int?> { result ->
                inputView.keyboardHeight = result
            })
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    /**
     * listViewAnimate
     *  0 listView不动
     *  1 上移动画
     * -1 回到初始位置
     */
    private fun listViewMove(animatorInput: Animator?, listViewAnimate: Int) {
        val keyBoardHeight = inputView.keyboardHeight
        keyBoardHeight ?: return
        if (animatorInput == null && listViewAnimate == 0) {
            return
        }
        val animatorSet = AnimatorSet()
        animatorSet.duration = 250
        animatorSet.interpolator = DecelerateInterpolator()
        if (listViewAnimate == 0) {
            if (animatorInput != null) {
                animatorSet.play(animatorInput)
                animatorSet.start()
            }
            return
        }
        val from = if (listViewAnimate==1) 0f else -keyBoardHeight.toFloat()
        val to = if (listViewAnimate==1) -keyBoardHeight.toFloat() else 0f
        val animatorListview: ObjectAnimator = ObjectAnimator.ofFloat(listView, "translationY", from, to)
        animatorListview.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (listViewAnimate==1) {
                    (listView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(listView.childCount - 1, Integer.MIN_VALUE)
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })


        animatorSet.play(animatorInput).with(animatorListview)
        animatorSet.start()
    }


}