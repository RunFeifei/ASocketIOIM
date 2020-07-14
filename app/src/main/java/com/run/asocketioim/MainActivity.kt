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
import java.net.URISyntaxException


class MainActivity : BaseActivity<BaseViewModel>() {

    private lateinit var socket: Socket
    private lateinit var callback: Emitter.Listener


    override fun initViewModel(): BaseViewModel {
        return BaseViewModel()
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initLivedata(viewModel: BaseViewModel) {
    }

    override fun initPage(savedInstanceState: Bundle?) {
        callback = Emitter.Listener {
            it?.let {
                if (it.isNotEmpty())
                    Log.e("TAG-->", "onConnect--${it.size}--${it.toString()}--${it[0]})")
            }
        }
        textView0.setOnClickListener {
            initSocketIO()
        }
        textView.setOnClickListener {
            socket.emit("ping")
        }

    }

    private fun initSocketIO() {
        socket = IO.socket("http://10.180.5.163:${edit.text}/")
        socket = socket.connect()
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(this, "${socket?.connected()}", Toast.LENGTH_SHORT).show()
        }, 200)
        try {
            socket?.on(Socket.EVENT_CONNECT, callback)
            socket?.on(Socket.EVENT_CONNECT_ERROR, callback)
            socket?.on(Socket.EVENT_DISCONNECT, callback)
            socket?.on("client", callback)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket?.disconnect()
    }
}
