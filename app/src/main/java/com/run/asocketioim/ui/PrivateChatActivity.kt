package com.run.asocketioim.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.run.asocketioim.R
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.viewmodel.OnlineUserItem
import com.run.asocketioim.viewmodel.OnlineUsersViewModel
import com.run.asocketioim.viewmodel.PrivateChatViewModel
import com.run.im.input.keyboard.KeyboardStateHelper
import com.run.im.input.keyboard.keyBoardHeight
import com.run.im.input.keyboard.keyBoardState
import kotlinx.android.synthetic.main.activity_chat_private.*
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.coroutines.launch
import zlc.season.permissionx.request
import zlc.season.yasha.linear


class PrivateChatActivity : BaseActivity<PrivateChatViewModel>() {

    private lateinit var onlineUsersViewModel: OnlineUsersViewModel


    override fun initViewModel(): PrivateChatViewModel {
        onlineUsersViewModel = ViewModelProvider(this).get(OnlineUsersViewModel::class.java)
        return ViewModelProvider(this).get(PrivateChatViewModel::class.java)
    }

    override fun layoutId(): Int {
        return R.layout.activity_chat_private
    }

    override fun initLivedata(viewModel: PrivateChatViewModel) {
        onlineUsersViewModel.getOnlineUsers()

        keyBoardState.observe(this, Observer<Boolean?> {
            it?.apply {
                showToast(if (this) "open" else "close")
            }
        })

        keyBoardHeight.observe(this, Observer<Int> {
            showToast("keyBoardHeight--${it}")
        })

        listView.linear(onlineUsersViewModel.dataSource) {
            renderItem<OnlineUserItem> {
                res(R.layout.item_user)
                onBind {
                    val position = onlineUsersViewModel.dataSource.getItems().indexOf(data)
                    textView.text = "MSG-${position}"
                    val colors = intArrayOf(Color.RED, Color.BLUE, Color.YELLOW)
                    containerView.setBackgroundColor(colors[position % 3])
                }
            }
        }
        listView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun initPage(savedInstanceState: Bundle?) {
        viewModel.viewModelScope.launch {
            request(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
        }
        KeyboardStateHelper(this).nada()
    }


}
