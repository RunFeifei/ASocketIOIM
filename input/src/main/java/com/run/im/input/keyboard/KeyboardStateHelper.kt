package com.run.im.input.keyboard

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.run.im.input.IMInput
import com.run.im.input.screenHeight

/**
 * Created by PengFeifei on 2020/9/7.
 * See
 * https://github.com/FreddyChen/KulaKeyboard/blob/master/app/src/main/java/com/freddy/kulakeyboard/sample/utils/SoftKeyboardStateHelper.kt
 */

val keyBoardState = MutableLiveData<Boolean?>()
val keyBoardHeight = MutableLiveData<Int>().apply {
    postValue((IMInput.context.screenHeight() * 0.4f).toInt())
}


class KeyboardStateHelper(owner: LifecycleOwner?, private var isSoftKeyboardOpened: Boolean = false) : ViewTreeObserver.OnGlobalLayoutListener {

    private var activityRootView: View? = null
    private var maxHeight = 0


    init {
        if (owner !is AppCompatActivity) {
            throw IllegalStateException("only support AppCompatActivity")
        }
        this.activityRootView = try {
            (owner.window.decorView.findViewById(android.R.id.content) as ViewGroup)[0]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        owner.getLifecycle().addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_STOP) {
                    doRelease()
                }
                if (event == Lifecycle.Event.ON_START) {
                    doObserve()
                }
            }
        })
    }

    override fun onGlobalLayout() {
        val rect = Rect()
        activityRootView!!.getWindowVisibleDisplayFrame(rect)
        if (rect.bottom > maxHeight) {
            maxHeight = rect.bottom
        }
        val heightDifference = maxHeight - rect.bottom
        val visible = heightDifference > IMInput.context.screenHeight() / 4
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened = true
            onKeyboardOpen(heightDifference)
        } else if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false
            onKeyboardClose()
        }
    }


    private fun onKeyboardOpen(keyboardHeightInPx: Int) {
        keyBoardState.value = true
        keyBoardHeight.value = keyboardHeightInPx
    }

    private fun onKeyboardClose() {
        keyBoardState.value = false
    }

    private fun doObserve() {
        activityRootView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
    }

    private fun doRelease() {
        activityRootView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }

    fun nada() {

    }
}