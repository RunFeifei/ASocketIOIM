package com.run.asocketioim

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : BaseActivity<BaseViewModel>() {

    private lateinit var socket: Socket

    override fun initViewModel(): BaseViewModel {
        return BaseViewModel()
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initLivedata(viewModel: BaseViewModel) {
    }

    override fun initPage(savedInstanceState: Bundle?) {
        connect.setOnClickListener {
            initSocketIO()
        }
        disconnect.setOnClickListener {
            socket.disconnect()
        }
        my_ping.setOnClickListener {
            socket.emit("my_ping", "my_ping")
        }
        my_event.setOnClickListener {
            val json = JSONObject()
            json.put("data", "******iuiiiuuuiuiuiuu*******")
            json.put("count", "123")
            socket.emit("my_event", json)
        }
        join_room0.setOnClickListener {
            val json = JSONObject()
            json.put("room", "room0")
            json.put("count", "123")
            socket.emit("join", json)
        }
        join_room1.setOnClickListener {
            val json = JSONObject()
            json.put("room", "room1")
            json.put("count", "123")
            socket.emit("join", json)
        }
        leave_room0.setOnClickListener {
            val json = JSONObject()
            json.put("room", "room0")
            json.put("count", "123")
            socket.emit("leave", json)
        }
        leave_room1.setOnClickListener {
            val json = JSONObject()
            json.put("room", "room1")
            json.put("count", "123")
            socket.emit("leave", json)
        }
        room_send.setOnClickListener {
            socket.emit("testroom")
        }
        text.setOnClickListener {
            val json = JSONObject()
            json.put("room", "room1")
            json.put("name", "123")
            socket.emit("text", json)
        }
    }

    private fun initSocketIO() {
        socket = IO.socket("http://10.180.5.163:5000")
        socket = socket.connect()
        Handler(Looper.getMainLooper()).postDelayed({
            toast(if (socket.connected()) "连接OK" else "连接错误")
        }, 200)
        onSocket()
    }

    private fun onSocket() {
        socket.on(Socket.EVENT_CONNECT, Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            if (it[0] is JSONObject) {
                Log.e("TAG-->", "connect--${it.size}--${it.toString()}--${it[0]})")
                toast("connect--${it.size}--${it.toString()}--${it[0]})")
            }
        })
        socket.on(Socket.EVENT_CONNECT_ERROR, Emitter.Listener {
            toast("connect_error")
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            if (it[0] is JSONObject) {
                Log.e("TAG-->", "connect_error--${it.size}--${it.toString()}--${it[0]})")
            }
        })
        socket.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            toast("disconnect")
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            if (it[0] is JSONObject) {
                Log.e("TAG-->", "disconnect--${it.size}--${it.toString()}--${it[0]})")
            }
        })
        socket.on("my_response", Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            toast(it[0].toString())
            Log.e("TAG-->", "my_response--${it[0]})")
        })
        socket.on("my response", Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            toast(it[0].toString())
            Log.e("TAG-->", "my response--${it[0]})")
        })
        socket.on("my_pong", Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            if (it[0] is JSONObject) {
                Log.e("TAG-->", "my_pong--${it.size}--${it.toString()}--${it[0]})")
                toast("my_pong--${it.size}--${it.toString()}--${it[0]})")
            }
        })
        socket.on("room", Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            Log.e("TAG-->", "room get ${it[0]}")
            toast("room get ${it[0]}")
        })
        socket.on("HeartBeat", Emitter.Listener {
            Log.e("TAG-->", "HeartBeat")
            if (it.isNullOrEmpty()) {
                return@Listener
            }
        })
        socket.on(Socket.EVENT_MESSAGE, Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            toast("${it[0]}")
            Log.e("TAG-->EVENT_MESSAGE", "${it[0]}")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }

    private fun toast(str: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
    }
}
