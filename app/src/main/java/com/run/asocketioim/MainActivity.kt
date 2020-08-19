package com.run.asocketioim

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.run.asocketioim.base.BaseActivity
import com.run.asocketioim.bean.Message
import com.run.asocketioim.viewmodel.MainViewModel
import com.run.asocketioim.widget.Common.getUser
import com.run.asocketioim.widget.LOCAL_IP
import com.run.asocketioim.widget.MessageType
import com.run.asocketioim.widget.getRandomText
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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        var name = getRandomText(5)
        edittext.text = "当前用户为${name}"
        edittext.setOnClickListener {
            name = getRandomText(5)
            edittext.text = "当前用户为${name}"
        }
        register.setOnClickListener {
            viewModel.register(name, "aabc")
        }
        login.setOnClickListener {
            viewModel.login(name, "aabc")
        }
        connect.setOnClickListener {
            initSocketIO()
        }
        disconnect.setOnClickListener {
            socket.disconnect()
        }
        text.setOnClickListener {
            val message = Message(
                text = "text",
                type = MessageType.MESSAGE_CHAT_PRIVATE,
                room_from = getUser().room_private,
                room_to = getUser().room_private
            )
            socket.emit(Socket.EVENT_MESSAGE, message.toJSONObject())
        }
        getUsers.setOnClickListener {
            viewModel.getUsers(1,10)
        }
        getOnlineUsers.setOnClickListener {
            viewModel.getOnlineUsers()
        }

    }

    private fun initSocketIO() {
        socket = IO.socket(LOCAL_IP)
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
            Log.e("EVENT_CONNECT", "链接成功")
            val message = Message(
                text = "join_room",
                type = MessageType.MESSAGE_JOIN_ROOM,
                room_from = getUser().room_private,
                room_to = getUser().room_private
            )
            socket.emit("connect_broadcast", message.toJSONObject())
        })
        socket.on(Socket.EVENT_CONNECT_ERROR, Emitter.Listener {
            toast("链接错误")
        })
        socket.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            toast("EVENT_DISCONNECT")
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
        socket.on("message_ack", Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            Log.e("TAG-->message_ack", "${it[0]}")
            //把消息标注为服务器收到
        })
        socket.on("join_room", Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            val message = Gson().fromJson(it[0].toString(), Message::class.java)
            val isSelf = getUser().room_private == message.room_from
            Log.e(
                "TAG-->join_room",
                "${it[0]}---${getUser().room_private}--${isSelf}"
            )
            if (isSelf) {
                toast("成功加入自己的房间")
            } else {
                toast("${getUser().id}进来了")
            }
        })
        socket.on("disconnect_broadcast", Emitter.Listener {
            if (it.isNullOrEmpty()) {
                return@Listener
            }
            val message = Gson().fromJson(it[0].toString(), Message::class.java)
            toast("${message.uid_from}离开了")
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
