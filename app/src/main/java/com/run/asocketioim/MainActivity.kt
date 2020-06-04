package com.run.asocketioim

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.base.BaseViewModel
import com.run.im.bean.IMMessage
import kotlinx.android.synthetic.main.activity_main.*
import org.litepal.LitePal
import org.litepal.LitePal.findAll
import org.litepal.extension.find


class MainActivity : BaseActivity<BaseViewModel>() {


    override fun initViewModel(): BaseViewModel {
        return BaseViewModel()
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initLivedata(viewModel: BaseViewModel) {
    }

    override fun initPage(savedInstanceState: Bundle?) {
        textView.setOnClickListener {
            val msg = IMMessage()
            msg.formUserID = -1
            msg.toUserID = -1
            msg.text = "123"
            msg.timeStamp = System.currentTimeMillis()
            msg.save()

            val datas: List<IMMessage> = findAll<IMMessage>(IMMessage::class.java)
            Log.e("MainActivity-->", Gson().toJson(datas))

            val data = LitePal.find<IMMessage>(0)
            Log.e("MainActivity-->", Gson().toJson(data))

        }
    }
}
