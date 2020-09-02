package com.run.im.input.emoji

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.run.im.input.Config
import com.run.im.input.R

/**
 * Created by PengFeifei on 2020/9/2.
 */

class EmotionListAdapter(val gridViewIndex: Int, layoutWidth: Int, layoutHeight: Int, val onEmojiClick: OnEmojiClick? = null) : RecyclerView.Adapter<EmotionListAdapter.ViewHolder>() {


    private var emojiSize: Float = -1f
    private var rowHeight: Float = -1f

    init {
        val emojiHeight = layoutHeight.toFloat() / Config.EMOJI_ROWS.toFloat() * 0.6f
        val emojiWidth = layoutWidth.toFloat() / Config.EMOJI_COLUMNS.toFloat() * 0.6f
        rowHeight = layoutHeight.toFloat() / Config.EMOJI_ROWS.toFloat()
        emojiSize = emojiHeight.coerceAtMost(emojiWidth)
    }


    override fun getItemCount(): Int {
        return Config.EMOJI_PER_PAGE + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView = RelativeLayout.LayoutParams(emojiSize.toInt(), emojiSize.toInt()).apply {
            addRule(RelativeLayout.CENTER_HORIZONTAL)
        }.let {
            val imageView = ImageView(parent.context)
            imageView.layoutParams = it
            imageView
        }
        val relativeLayout: RelativeLayout = RelativeLayout(parent.context).apply {
            gravity = Gravity.CENTER
            layoutParams = AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, rowHeight.toInt())
            addView(imageView)
        }
        return ViewHolder(relativeLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.layout.getChildAt(0) as ImageView) {
            val isLast = position == itemCount - 1
            val index = gridViewIndex * Config.EMOJI_PER_PAGE + position
            val emotionPath = EmojiLoadManager.getEmotionUrl(index)
            //click
            setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    if (isLast) {
                        onEmojiClick?.onEmojiDelete(v)
                        return
                    }
                    onEmojiClick?.onEmojiSelected(emotionPath, v)
                }
            })
            //render
            if (isLast) {
                setBackgroundResource(R.drawable.ic_emoji_del)
                return@with
            }

            emotionPath?.let {
                Glide.with(context)
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this)
            }
        }
    }

    class ViewHolder(val layout: RelativeLayout) : RecyclerView.ViewHolder(layout)
}


interface OnEmojiClick {
    fun onEmojiSelected(emotionPath: String?, view: View?)
    fun onEmojiDelete(View: View?)

}
