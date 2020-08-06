package com.run.asocketioim

import android.content.Context
import com.uestc.request.handler.Request
import org.litepal.LitePalApplication

/**
 * Created by PengFeifei on 2020/8/5.
 */
class App : LitePalApplication() {

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
    }

    private fun initRetrofit() {
        Request.init(getApp(), "http://10.180.5.163:5000") {
            okHttp {
                //配置okhttp
                it
            }

            retrofit {
                //配置retrofit
                it
            }
        }
    }

    private fun getApp(): Context {
        return getContext()
    }
}