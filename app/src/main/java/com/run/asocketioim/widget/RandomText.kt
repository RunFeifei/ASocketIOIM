package com.run.asocketioim.widget

import kotlin.random.Random

/**
 * Created by PengFeifei on 2020/8/7.
 */


fun getRandomText(len: Int): String {
    val data: String =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    val sb = StringBuilder(len)
    for (i in 0 until len) {
        sb.append(data.toCharArray()[Random.Default.nextInt(data.length)])
    }
    return sb.toString()
}