package com.run.asocketioim.widget

import com.forjrking.preferences.kt.PreferenceHolder
import com.run.asocketioim.bean.User

/**
 * Created by PengFeifei on 2020/8/6.
 */

object Common {
    var user: User? = null

    internal fun getUser(): User {
        user?.let {
            return it
        }
        return APPsp.user
    }
}

/*
 * @param name xml名称 null 默认为实现类类名，为了防止不同类使用相同字段覆盖数据问题
 * @param cryptKey 加密密钥 null 表示不用加密
 * @param isMMKV  是否使用mmkv 默认false
 * @param isMultiProcess 是否使用多进程 建议mmkv搭配使用 sp性能很差 默认false
 */
object APPsp : PreferenceHolder("APPsp", null, false, false) {
    var user: User by bindToPreferenceField(User())
}