package com.run.asocketioim.bean

import com.google.gson.Gson
import com.run.asocketioim.widget.Common.getUser
import org.json.JSONObject
import java.io.Serializable

/**
 * Created by PengFeifei on 2020/8/4.
 */
class Message : Serializable {

    internal var id: Int = 0
    internal var text: String = ""
    internal var type: Int = 0
    internal var is_send_to_server: Boolean = true
    internal var time_client: Long = 0
    internal var room_from: String = ""
    internal var room_to: String = ""
    internal var uid_from: Long = 0
    internal var uid_to: Long? = 0

    constructor(
        text: String,
        type: Int,
        is_send_to_server: Boolean = true,
        time_client: Long = System.currentTimeMillis(),
        room_from: String,
        room_to: String,
        uid_from: Long = getUser().id,
        uid_to: Long? = null
    ) {
        this.text = text
        this.type = type
        this.is_send_to_server = is_send_to_server
        this.time_client = time_client
        this.room_from = room_from
        this.room_to = room_to
        this.uid_to = uid_to
        this.uid_from = uid_from
    }


    override fun toString(): String {
        return Gson().toJson(this)
    }

    fun toJSONObject(): JSONObject {
        val json = JSONObject()
        json.put("text", text)
        json.put("type", type)
        json.put("is_send_to_server", is_send_to_server)
        json.put("time_client", time_client)
        json.put("room_from", room_from)
        json.put("room_to", room_to)
        json.put("uid_from", uid_from)
        uid_to?.let {
            json.put("uid_to", it)
        }
        return json
    }
}