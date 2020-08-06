package com.run.asocketioim.bean

import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by PengFeifei on 2020/8/5.
 */
class User : Serializable {

    internal var access_token: String = ""
    internal var refresh_token: String = ""
    internal var room_private: String = ""
    internal var user_id: Int = 0
    internal var username: String = ""

    override fun toString(): String {
        return Gson().toJson(this)
    }

}