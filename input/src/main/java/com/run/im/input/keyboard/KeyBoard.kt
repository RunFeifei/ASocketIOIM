package com.run.im.input.keyboard

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by PengFeifei on 2020/9/4.
 */

fun EditText?.showKeyboard() {
    this ?: return
    context ?: return
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText?.hideKeyboard() {
    this ?: return
    context ?: return
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Activity?.hideKeyboard() {
    this ?: return
    window ?: return
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}