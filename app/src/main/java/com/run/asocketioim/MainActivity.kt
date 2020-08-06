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
import com.run.asocketioim.bean.Message
import com.run.asocketioim.viewmodel.MainViewModel
import com.run.asocketioim.widget.Common.getUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var socket: Socket

    override fun initViewModel(): MainViewModel {
        return MainViewModel()
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initLivedata(viewModel: MainViewModel) {
    }

    override fun initPage(savedInstanceState: Bundle?) {
        login.setOnClickListener {
            viewModel.login("aaa", "aabc")
        }
        connect.setOnClickListener {
            initSocketIO()
        }
        disconnect.setOnClickListener {
            socket.disconnect()
        }
    }

    private fun initSocketIO() {
        socket = IO.socket("http://10.180.5.163:5000")
        socket = socket.connect()
        Handler(Looper.getMainLooper()).postDelayed({
            if (socket.connected()) {
                return@postDelayed
            }
            toast("连接错误")
        }, 200)
        onSocket()
    }

    private fun onSocket() {
        socket.on(Socket.EVENT_CONNECT, Emitter.Listener {
            toast("连接成功")
            val message = Message(
                text = "join_room",
                room_from = getUser().room_private,
                room_to = getUser().room_private
            )
            socket.emit("join_room", message.toJSONObject())
        })
        socket.on(Socket.EVENT_CONNECT_ERROR, Emitter.Listener {
            toast("链接错误")
        })
        socket.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            toast("链接断了")
        })
        socket.on("heart_beat", Emitter.Listener {
            Log.e("TAG-->", "HeartBeat")
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
