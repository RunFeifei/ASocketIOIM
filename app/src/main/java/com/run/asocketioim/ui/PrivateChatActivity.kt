package com.run.asocketioim.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope
import com.run.asocketioim.R
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.base.BaseViewModel
import com.run.im.input.emoji.OnEmojiClick
import kotlinx.android.synthetic.main.activity_chat_private.*
import kotlinx.coroutines.launch
import zlc.season.bracer.start
import zlc.season.permissionx.request


class PrivateChatActivity : BaseActivity<BaseViewModel>() {

    override fun initViewModel(): BaseViewModel {
        return BaseViewModel()
    }

    override fun layoutId(): Int {
        return R.layout.activity_chat_private
    }

    override fun initLivedata(viewModel: BaseViewModel) {
    }

    override fun initPage(savedInstanceState: Bundle?) {
        viewModel.viewModelScope.launch {
            request(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
        }

        textBtn.setOnClickListener {
            PrivateChatActivity().start(this)
        }
        emotion.onEmojiClick = object : OnEmojiClick {
            override fun onEmojiSelected(emotionPath: String?, view: View?) {
                showToast("onEmojiSelected")
            }

            override fun onEmojiDelete(View: View?) {
                showToast("onEmojiDelete")
            }
        }

    }


}
