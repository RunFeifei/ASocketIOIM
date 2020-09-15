package com.run.im

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.run.im.input.keyboard.KeyboardStateHelper
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
    }

    fun setActivity(owner: LifecycleOwner?) {
        owner?.let { own ->
            this.owner = own
            KeyboardStateHelper(own).nada()
            keyBoardState.observe(own, Observer<Boolean?> {
                it?.apply {
                    showToast(if (this) "open" else "close")
                    inputView.onKeyboard(this, keyBoardHeight.value!!)
                }
            })

            keyBoardHeight.observe(own, Observer<Int> { result ->
                showToast("keyBoardHeight--${result}")
                inputView.onKeyboard(keyBoardState.value!!, result)
            })
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}