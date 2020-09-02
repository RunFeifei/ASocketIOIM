package com.run.im.input.emoji

/**
 * Created by PengFeifei on 2020/9/2.
 */
data class EmojiEntity(
    val PopoEmoticons: PopoEmoticons
)

data class PopoEmoticons(
    val Catalog: Catalog
)

data class Catalog(
    val Emoticon: List<Emoticon>,
    val Title: String
)

data class Emoticon(
    val File: String,
    val ID: String,
    val Tag: String
)

class EmojiEntry(var text: String, var assetPath: String) {
    companion object {
        val EmptyEmojiEntry = EmojiEntry("", "")
    }
}
