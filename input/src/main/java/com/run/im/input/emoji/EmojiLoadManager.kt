package com.run.im.input.emoji

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.LruCache
import android.util.Xml
import com.run.im.input.Config.Companion.EMOJI_PER_PAGE
import com.run.im.input.Config.Companion.EMOT_DIR
import com.run.im.input.IMInput
import com.run.im.input.emoji.EmojiLoadManager.EmojiEntry.Companion.EmptyEmojiEntry
import com.run.im.input.getBitmapFromAsset
import kotlinx.coroutines.*
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

/**
 * Created by PengFeifei on 2020/8/31.
 */


class EmojiLoadManager {

    companion object {
        private val listEmoji = mutableListOf<EmojiEntry>()
        private val drawableCache by lazy {
            DrawableCache(1024)
        }

        fun getDisplayCount(): Int {
            return listEmoji.size
        }

        init {
            loadInit()
        }

        private fun loadInit() {
            DefaultEmojiLoader().load(IMInput.context, "${EMOT_DIR}emoji.xml")
            //是否填满一整页
            val isJustWholePage = listEmoji.size % EMOJI_PER_PAGE == 0
            if (!isJustWholePage) {
                val lastPageEmojiNum = listEmoji.size % EMOJI_PER_PAGE
                for (i in lastPageEmojiNum..EMOJI_PER_PAGE) {
                    listEmoji.add(EmptyEmojiEntry)
                }
            }
        }

        /**
         * 此时loadInit()已经执行过了
         * 按道理来说这里应该是整除
         *
         * 1. 伴生对象中成员变量初始化
         * 2. 伴生对象中init代码块按先后顺序执行
         * 3. 类的init代码块按先后顺序执行
         * 4. 类的主构造函数
         * 5. 类的次构造函数
         */
        fun getDisplayPageCount(): Int {
            if (listEmoji.size % EMOJI_PER_PAGE != 0) {
                throw IllegalStateException("按道理来说这里应该是整除")
            }
            return listEmoji.size / EMOJI_PER_PAGE
        }

        /**
         * launch(Dispatchers.Main) {
         * val image = loadEmotion()
         * avatarIv.setImageBitmap(image)
         * }
         */
        suspend fun loadEmotion(context: Context?, index: Int): BitmapDrawable? {
            return withContext(Dispatchers.IO) {
                context ?: return@withContext null
                val entry = if (index < 0 || index >= listEmoji.size) null else listEmoji[index]
                entry ?: return@withContext null
                if (entry.text.isEmpty()) {
                    return@withContext null
                }
                val cache = drawableCache.get(entry.assetPath)
                if (cache != null) {
                    cache
                } else {
                    val drawable = getBitmapFromAsset(context, entry.assetPath)
                    drawableCache.put(entry.assetPath, drawable)
                    drawable
                }
            }
        }

    }





    class DefaultEmojiLoader : DefaultHandler() {

        fun load(context: Context, assetPath: String) {
            try {
                context.assets.open(assetPath)
            } catch (ignore: Exception) {
                ignore.printStackTrace()
                null
            }?.let { input ->
                try {
                    Xml.parse(input, Xml.Encoding.UTF_8, this)
                } catch (ignore: Exception) {
                    ignore.printStackTrace()
                } finally {
                    try {
                        input.close()
                    } catch (ignore: java.lang.Exception) {
                        ignore.printStackTrace()
                    }
                }
                input
            }

        }


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
//                text2Entry[Integer.decode(tag)] = entry
                if (catlog == "default") {
                    listEmoji.add(entry)
                }

            }
        }
    }

    class EmojiEntry(var text: String, var assetPath: String) {
        companion object {
            val EmptyEmojiEntry = EmojiEntry("", "")
        }
    }

    class DrawableCache(size: Int) : LruCache<String, BitmapDrawable?>(size) {
        override fun entryRemoved(evicted: Boolean, key: String?, oldValue: BitmapDrawable?, newValue: BitmapDrawable?) {
            if (oldValue == null || newValue == null) {
                return
            }
            if (oldValue != newValue) {
                oldValue.bitmap?.recycle()
            }
        }
    }

}