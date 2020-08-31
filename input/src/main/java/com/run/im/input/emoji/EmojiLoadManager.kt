package com.run.im.input.emoji

import android.graphics.Bitmap
import android.util.LruCache
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

/**
 * Created by PengFeifei on 2020/8/31.
 */
const val EMOT_DIR = "emoji/"
private val listEmoji = mutableListOf<EmojiEntry>()
private val text2Entry = hashMapOf<Int, EmojiEntry>()
private val drawableCache by lazy {
    DrawableCache(1024)
}




class DefaultEmojiLoader : DefaultHandler() {
    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        if (localName.isNullOrBlank() || attributes == null) {
            return
        }
        var catlog = ""
        catlog = if (localName == "Catalog") {
            attributes.getValue(uri, "Title")
        } else {
            catlog
        }
        if (localName == "Emoticon") {
            val tag = attributes.getValue(uri, "Tag")
            val fileName = attributes.getValue(uri, "File")
            val entry = EmojiEntry(tag, "${EMOT_DIR}${catlog}/${fileName}")
            text2Entry[Integer.decode(tag)] = entry
            if (catlog == "default") {
                listEmoji.add(entry)
            }

        }
    }
}

class EmojiEntry(var text: String, var assetPath: String)

class DrawableCache(size: Int) : LruCache<String, Bitmap>(size) {
    override fun entryRemoved(evicted: Boolean, key: String?, oldValue: Bitmap?, newValue: Bitmap?) {
        if (oldValue == null || newValue == null) {
            return
        }
        if (oldValue != newValue) {
            oldValue.recycle()
        }
    }
}