package com.run.asocketioim.viewmodel

import android.util.Log
import com.run.asocketioim.base.BaseViewModel
import com.run.asocketioim.bean.User
import com.run.asocketioim.net.API
import com.run.asocketioim.widget.APPsp
import com.run.asocketioim.widget.Common
import com.uestc.request.handler.Request

/**
 * Created by PengFeifei on 2020/8/5.
 */
class MainViewModel : BaseViewModel() {

    private val service by lazy { Request.apiService(API::class.java) }


    fun login(name: String, password: String) {
        apiDSL<User> {
            onRequest {
                service.login(name, password)
            }
            onResponse {
                Log.e("TAG-->", it.toString())
                showToast("登录成功")
                Common.user = it
                APPsp.user = it
            }
            onError {
                showToast("登录失败")
                false
            }
        }
    }


}