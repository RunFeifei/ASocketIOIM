package com.run.asocketioim.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.run.asocketioim.R
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.base.BaseViewModel
import com.run.im.input.keyboard.KeyboardStateHelper
import com.run.im.input.keyboard.keyBoardHeight
import com.run.im.input.keyboard.keyBoardState
import kotlinx.coroutines.launch
import zlc.season.permissionx.request


class PrivateChatActivity : BaseActivity<BaseViewModel>() {


    override fun initViewModel(): BaseViewModel {
        return BaseViewModel()
    }

    override fun layoutId(): Int {
        return R.layout.activity_chat_private
    }

    override fun initLivedata(viewModel: BaseViewModel) {
        keyBoardState.observe(this, Observer<Boolean?> {
            it?.apply {
                showToast(if (this) "open" else "close")
            }
        })

        keyBoardHeight.observe(this, Observer<Int> {
            showToast("keyBoardHeight--${it}")
        })
    }

    override fun initPage(savedInstanceState: Bundle?) {
        viewModel.viewModelScope.launch {
            request(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
        }
        KeyboardStateHelper(this).nada()
    }


}
