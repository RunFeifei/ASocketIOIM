package com.run.im.input.keyboard

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.PopupWindow
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

val keyBoardState = MutableLiveData<Boolean>(false)
val keyBoardHeight = MutableLiveData<Int>((IMInput.context.screenHeight() * 0.4f).toInt())


/**
 * 此方案不适应与adjustNothing
 */
class KeyboardStateHelper(owner: LifecycleOwner, private var isSoftKeyboardOpened: Boolean = false) : ViewTreeObserver.OnGlobalLayoutListener {

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

class AdjustNothingKeyboardStateHelper(owner: LifecycleOwner, private var isSoftKeyboardOpened: Boolean = false) : PopupWindow(), ViewTreeObserver.OnGlobalLayoutListener {


    private var maxHeight = 0
    private var activityRootView: View? = null


    init {
        if (owner !is AppCompatActivity) {
            throw IllegalStateException("only support AppCompatActivity")
        }
        activityRootView = try {
            (owner.window.decorView.findViewById(android.R.id.content) as ViewGroup)[0]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        width = 0
        height = ViewGroup.LayoutParams.MATCH_PARENT
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        contentView = View(owner)
        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        inputMethodMode = INPUT_METHOD_NEEDED

        owner.getLifecycle().addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_STOP) {
                    doObserve()
                }
                if (event == Lifecycle.Event.ON_START) {
                    doRelease()
                }
                if (event == Lifecycle.Event.ON_DESTROY) {
                    dismiss()
                }

            }
        })
        activityRootView?.post {
            showAtLocation(
                activityRootView,
                Gravity.NO_GRAVITY,
                0,
                0
            )
        }


    }

    override fun onGlobalLayout() {
        contentView ?: return
        val rect = Rect()
        contentView!!.getWindowVisibleDisplayFrame(rect)
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

    private fun doObserve() {
        activityRootView?.postDelayed({
            contentView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
        }, 1000)

    }

    private fun doRelease() {
        contentView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }

    private fun onKeyboardOpen(keyboardHeightInPx: Int) {
        keyBoardState.value = true
        keyBoardHeight.value = keyboardHeightInPx
    }

    private fun onKeyboardClose() {
        keyBoardState.value = false
    }

    fun nada() {
        Log.e("AdjustNothing", "AdjustNothingKeyboardStateHelper")
    }
}