package com.run.asocketioim.viewmodel

import android.util.Log
import com.google.gson.Gson
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
                Request.putHead("Authorization", " Bearer ${it.refresh_token}")
            }
            onError {
                showToast("登录失败")
                false
            }
        }
    }

    fun register(name: String, password: String) {
        apiDSL<Any> {
            onRequest {
                service.register(name, password)
            }
            onResponse {
                Log.e("TAG-->", it.toString())
                showToast("注册成功")
            }
            onError {
                showToast("注册失败")
                false
            }
        }
    }

    fun getUsers(page: Int, per_page: Int) {
        apiDSL<List<User>> {
            onRequest {
                service.getUsers(page, per_page)
            }
            onResponse {
                Log.e("TAG-->", Gson().toJson(it))
            }
            onError {
                showToast("getUsers失败")
                false
            }
        }
    }

    fun getOnlineUsers() {
        apiDSL<List<User>> {
            onRequest {
                service.getOnlineUsers()
            }
            onResponse {
                Log.e("TAG-->", Gson().toJson(it))
            }
            onError {
                showToast("getUsers失败")
                false
            }
        }
    }


}