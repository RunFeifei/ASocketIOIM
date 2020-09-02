package com.run.asocketioim.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.run.asocketioim.R
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.base.BaseViewModel
import com.run.im.input.Config
import com.run.im.input.emoji.EmotionListAdapter
import com.run.im.input.screenHeight
import com.run.im.input.screenWidth
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
            val result = request(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
            if (result.isGranted) {
                //Now we have storage permission.
            } else {
                if (result.shouldShowRational) {
                    //Show permission rational
                }
                if (result.alwaysDenied) {
                    //User always denied our permission
                }
            }
        }

        textBtn.setOnClickListener {
            PrivateChatActivity().start(this)
        }

    }

    private fun RecyclerView.setUpRecyclerView(position: Int) {
        layoutManager = GridLayoutManager(context, Config.EMOJI_COLUMNS, RecyclerView.VERTICAL, false)
        adapter = EmotionListAdapter(position, screenWidth(), screenHeight())
    }


}
