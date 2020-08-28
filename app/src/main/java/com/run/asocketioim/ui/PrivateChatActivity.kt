package com.run.asocketioim.ui

import android.os.Bundle
import com.run.asocketioim.R
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.base.BaseViewModel
import com.vanniktech.emoji.EmojiPopup
import kotlinx.android.synthetic.main.activity_chat_private.*


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
        textBtn.setOnClickListener {
        }

    }


}
