package com.run.im.input.emoji

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.run.im.input.Config.Companion.EMOJI_COLUMNS
import com.run.im.input.R

/**
 * Created by PengFeifei on 2020/8/28.
 */
class EmotionLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    private var layoutHeight: Int = -1
    private var layoutWidth: Int = -1
    private var emotionViewPager: ViewPager2

    var onEmojiClick: OnEmojiClick? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_emotion, this)
        emotionViewPager = findViewById(R.id.emotionViewPager)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        (visibility == View.VISIBLE).let {
            Log.e("onVisibilityChanged", visibility.toString())
            post {
                initViewPager()
            }
        }
    }

    private fun initViewPager() {
        emotionViewPager.adapter = EmotionViewpagerAdapter(context, layoutWidth, layoutHeight).apply {
            onEmojiClick = this@EmotionLayout.onEmojiClick
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED || MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            throw IllegalStateException("此view的状态禁止设置为wrap,推荐match")
        }
        layoutWidth = MeasureSpec.getSize(widthMeasureSpec)
        layoutHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(layoutWidth, layoutHeight)
    }
}


class EmotionViewpagerAdapter(val context: Context, val layoutWidth: Int, val layoutHeight: Int) : RecyclerView.Adapter<EmotionViewpagerViewHolder>() {

    var onEmojiClick: OnEmojiClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewpagerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_emotion_viewpager, parent, false)
        return EmotionViewpagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return EmojiLoadManager.getDisplayPageCount()
    }

    override fun onBindViewHolder(holder: EmotionViewpagerViewHolder, position: Int) {
        holder.apply {
            listView.setUpRecyclerView(position)
        }
    }

    private fun RecyclerView.setUpRecyclerView(position: Int) {
        layoutManager = GridLayoutManager(context, EMOJI_COLUMNS, RecyclerView.VERTICAL, false)
        adapter = EmotionListAdapter(position, layoutWidth, layoutHeight, onEmojiClick)
    }

}

class EmotionViewpagerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val listView: RecyclerView = itemView.findViewById(R.id.listView)
}

