package com.run.im.input.emoji

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.LruCache
import com.google.gson.Gson
import com.run.im.input.Config.Companion.EMOJI_PER_PAGE
import com.run.im.input.Config.Companion.EMOT_DIR
import com.run.im.input.IMInput
import com.run.im.input.emoji.EmojiEntry.Companion.EmptyEmojiEntry
import com.run.im.input.getBitmapFromAsset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.Charset

/**
 * Created by PengFeifei on 2020/8/31.
 */


class EmojiLoadManager {

    companion object {
        private lateinit var listEmoji: ArrayList<EmojiEntry>
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
            listEmoji = getListEmoji(IMInput.context) as ArrayList<EmojiEntry>
            //是否填满一整页
            val isJustWholePage = listEmoji.size % EMOJI_PER_PAGE == 0
            if (!isJustWholePage) {
                val lastPageEmojiNum = listEmoji.size % EMOJI_PER_PAGE
                for (i in lastPageEmojiNum until EMOJI_PER_PAGE) {
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

        private fun getListEmoji(context: Context): List<EmojiEntry> {
            val assetPath = "${EMOT_DIR}emoji.json"
            val bufferSize = 1024

            val input = try {
                context.assets.open(assetPath)
            } catch (ignore: Exception) {
                ignore.printStackTrace()
                null
            }
            input ?: throw java.lang.IllegalStateException("读取asset失败")

            val out = try {
                val bytes = ByteArray(bufferSize)
                var count: Int
                val out = ByteArrayOutputStream()
                while (input.read(bytes, 0, bufferSize).also { count = it } != -1) {
                    out.write(bytes, 0, count)
                }
                out
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            out ?: throw java.lang.IllegalStateException("读取asset-input失败")
            val listdataAndTitle = try {
                val json = String(out.toByteArray(), Charset.forName("UTF-8"))
                val entity = Gson().fromJson(json, EmojiEntity::class.java)
                val list = entity.PopoEmoticons.Catalog.Emoticon
                val title = entity.PopoEmoticons.Catalog.Title
                Pair(list, title)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            } finally {
                try {
                    input.close()
                    out.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            listdataAndTitle ?: throw java.lang.IllegalStateException("转换List<Emoticon>失败")
            return listdataAndTitle.first.map {
                EmojiEntry(it.Tag, "${EMOT_DIR}${listdataAndTitle.second}/${it.File}")
            }
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