package com.run.asocketioim.ui

import android.os.Bundle
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
        listView.setUpRecyclerView(1)

    }

    private fun RecyclerView.setUpRecyclerView(position: Int) {
        layoutManager = GridLayoutManager(context, Config.EMOJI_COLUMNS, RecyclerView.VERTICAL, false)
        adapter = EmotionListAdapter(position, screenWidth(), screenHeight())
    }


}
