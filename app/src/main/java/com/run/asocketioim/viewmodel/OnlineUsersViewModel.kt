package com.run.asocketioim.viewmodel

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.run.asocketioim.base.BaseViewModel
import com.run.asocketioim.bean.User
import com.run.asocketioim.net.API
import com.uestc.request.handler.Request
import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem


/**
 * Created by PengFeifei on 2020/8/26.
 */
class OnlineUsersViewModel : BaseViewModel() {

    val dataSource = OnlineUsersDataSource()
    private val service by lazy { Request.apiService(API::class.java) }

    fun getOnlineUsers() {
        apiDSL<List<User>> {
            onRequest {
                service.getOnlineUsers()
            }
            onResponse { list ->
                Log.e("TAG-->", Gson().toJson(list))
                dataSource.addItems(list.map { user ->
                    OnlineUserItem(user)
                })
            }
            onError {
                showToast("getUsers失败")
                false
            }
        }
    }


}

class OnlineUserItem(val data: User) : YashaItem
class OnlineUsersDataSource : YashaDataSource() {
    override suspend fun loadInitial(): List<OnlineUserItem>? {
        val json =
            "[{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"},{\"username\":\"123\"}]"
        val users: List<User> = Gson().fromJson(json, object : TypeToken<List<User?>?>() {}.type)
        return users.map {
            OnlineUserItem(it)
        }.subList(0,10)

    }
}

