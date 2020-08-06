package com.run.asocketioim.bean

import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by PengFeifei on 2020/8/4.
 */
class Message : Serializable {

    internal var text: String? = ""
    internal var is_send_to_server: Boolean? = true
    internal var time_client: Long? = 0
    internal var room_from: String? = ""
    internal var room_to: String? = ""
    internal var uid_to: Long? = 0
    internal var uid_from: Long? = 0

    override fun toString(): String {
        return Gson().toJson(this)
    }
}