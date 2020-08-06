package com.run.asocketioim.viewmodel

import androidx.lifecycle.MutableLiveData
import com.run.asocketioim.base.BaseViewModel
import com.run.asocketioim.bean.User
import com.run.asocketioim.net.API
import com.uestc.request.handler.Request

/**
 * Created by PengFeifei on 2020/8/5.
 */
class MainViewModel : BaseViewModel() {

    private val service by lazy { Request.apiService(API::class.java) }

    val login = MutableLiveData<User>()

    fun login(name: String, password: String) {
        apiDSL<User> {
            onRequest {
                service.login(name, password)
            }
            onResponse {
                login.value = it
            }
        }
    }


}