package com.run.im.input

/**
 * Created by PengFeifei on 2020/8/28.
 */
class Config {

    companion object {
        val EMOJI_COLUMNS = 7
        val EMOJI_ROWS = 3
        val EMOJI_PER_PAGE = EMOJI_COLUMNS * EMOJI_ROWS - 1 //最后一个是删除键

        val SPECIAL_COLUMNS = 4//输入面板点击+



        val STICKER_COLUMNS = 4
        val STICKER_ROWS = 2
        val STICKER_PER_PAGE = STICKER_COLUMNS * STICKER_ROWS

        val EMOT_DIR = "emoji/"
    }


}