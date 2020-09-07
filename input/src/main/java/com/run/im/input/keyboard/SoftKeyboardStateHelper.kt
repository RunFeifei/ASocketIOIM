package com.run.im.input.keyboard

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.MutableLiveData
import com.run.im.input.IMInput
import com.run.im.input.screenHeight

/**
 * @author  FreddyChen
 *
 * https://github.com/FreddyChen/KulaKeyboard/blob/master/app/src/main/java/com/freddy/kulakeyboard/sample/utils/SoftKeyboardStateHelper.kt
 */

val keyBoardState = MutableLiveData<Boolean>()
val keyBoardHeight = MutableLiveData<Int>().apply {
    value = 0
}


class SoftKeyboardStateHelper : ViewTreeObserver.OnGlobalLayoutListener {

    private var activityRootView: View? = null
    private var isSoftKeyboardOpened = false

    constructor(activityRootView: View?) : this(activityRootView, false)

    constructor(activityRootView: View?, isSoftKeyboardOpened: Boolean) {
        this.activityRootView = activityRootView
        this.isSoftKeyboardOpened = isSoftKeyboardOpened
        activityRootView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
    }

    private var maxHeight = 0
    override fun onGlobalLayout() {
        val rect = Rect()
        activityRootView!!.getWindowVisibleDisplayFrame(rect)
        if (rect.bottom > maxHeight) {
            maxHeight = rect.bottom
        }
        val screenHeight: Int = IMInput.context.screenHeight()
        val heightDifference = maxHeight - rect.bottom
        val visible = heightDifference > screenHeight / 4
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

    fun release() {
        activityRootView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }
}