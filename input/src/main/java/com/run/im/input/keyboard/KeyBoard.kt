package com.run.im.input.keyboard

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by PengFeifei on 2020/9/4.
 */

fun EditText?.showKeyboard(context: Context) {
    this ?: return
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText?.hideKeyboard(context: Context) {
    this ?: return
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.windowToken, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity?.hideKeyboard() {
    this ?: return
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, InputMethodManager.SHOW_IMPLICIT)
}