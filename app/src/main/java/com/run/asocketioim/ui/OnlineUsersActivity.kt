package com.run.asocketioim.ui

import android.os.Bundle
import android.view.WindowManager
import com.run.asocketioim.R
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.viewmodel.OnlineUserItem
import com.run.asocketioim.viewmodel.OnlineUsersViewModel
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.item_user.*
import zlc.season.bracer.start
import zlc.season.yasha.linear


class OnlineUsersActivity : BaseActivity<OnlineUsersViewModel>() {

    override fun initViewModel(): OnlineUsersViewModel {
        return OnlineUsersViewModel()
    }

    override fun layoutId(): Int {
        return R.layout.activity_users
    }

    override fun initLivedata(viewModel: OnlineUsersViewModel) {
        viewModel.getOnlineUsers()
    }

    override fun initPage(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        listView.linear(viewModel.dataSource)
        {
            renderItem<OnlineUserItem> {
                res(R.layout.item_user)
                onBind {
                    textView.text = data.data.username
                    containerView.setOnClickListener {
                        PrivateChatActivity().start(this@OnlineUsersActivity)
                    }
                }
            }
        }
    }


}
