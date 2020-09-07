package com.run.im.input.keyboard

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import com.run.im.input.IMInput
import com.run.im.input.screenHeight
import java.util.*

/**
 * @author  FreddyChen
 * @name
 * @date    2020/06/08 14:42
 * @email   chenshichao@outlook.com
 * @github  https://github.com/FreddyChen
 * @desc
 */
class SoftKeyboardStateHelper : ViewTreeObserver.OnGlobalLayoutListener {

    interface SoftKeyboardStateListener {
        fun onSoftKeyboardOpened(keyboardHeight: Int)
        fun onSoftKeyboardClosed()
    }

    private val listeners: MutableList<SoftKeyboardStateListener> =
        LinkedList()
    private var activityRootView: View? = null
    private var lastSoftKeyboardHeightInPx = 0
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
            notifyOnSoftKeyboardOpened(heightDifference)
        } else if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false
            notifyOnSoftKeyboardClosed()
        }
    }


    fun setIsSoftKeyboardOpened(isSoftKeyboardOpened: Boolean) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened
    }

    fun isSoftKeyboardOpened(): Boolean {
        return isSoftKeyboardOpened
    }

    fun getLastSoftKeyboardHeightInPx(): Int {
        return lastSoftKeyboardHeightInPx
    }

    fun addSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.add(listener)
    }

    fun removeSoftKeyboardStateListener(listener: SoftKeyboardStateListener) {
        listeners.remove(listener)
    }

    private fun notifyOnSoftKeyboardOpened(keyboardHeightInPx: Int) {
        lastSoftKeyboardHeightInPx = keyboardHeightInPx
        for (listener in listeners) {
            listener.onSoftKeyboardOpened(keyboardHeightInPx)
        }
    }

    private fun notifyOnSoftKeyboardClosed() {
        for (listener in listeners) {
            listener.onSoftKeyboardClosed()
        }
    }

    fun release() {
        activityRootView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }
}