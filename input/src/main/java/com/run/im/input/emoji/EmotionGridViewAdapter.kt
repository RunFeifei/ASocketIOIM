package com.run.im.input.emoji

import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem

/**
 * Created by PengFeifei on 2020/8/28.
 */

class EmotionItem(val data: String) : YashaItem
class EmotionDataSource : YashaDataSource() {
    override suspend fun loadInitial(): List<EmotionItem>? {
        val list = mutableListOf<EmotionItem>()
        val item = EmotionItem("sd")
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        return list
    }
}