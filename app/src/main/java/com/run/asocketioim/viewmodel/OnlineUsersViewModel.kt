package com.run.asocketioim.viewmodel

import android.util.Log
import com.google.gson.Gson
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
        val list = mutableListOf<OnlineUserItem>()
        val user = User()
        user.username = "123"
        val item = OnlineUserItem(user)
        list.add(item)
        return list

    }
}

