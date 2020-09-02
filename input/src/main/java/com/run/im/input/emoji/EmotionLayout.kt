package com.run.im.input.emoji

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.run.im.input.Config.Companion.EMOJI_COLUMNS
import com.run.im.input.Config.Companion.EMOJI_PER_PAGE
import com.run.im.input.Config.Companion.EMOJI_ROWS
import com.run.im.input.R

/**
 * Created by PengFeifei on 2020/8/28.
 */
class EmotionLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    private var layoutHeight: Int = -1
    private var layoutWidth: Int = -1
    private var emotionViewPager: ViewPager2

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_emotion, this)
        emotionViewPager = findViewById(R.id.emotionViewPager)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post {
            initViewPager()
        }
    }

    private fun initViewPager() {
        emotionViewPager.adapter = EmotionViewpagerAdapter(context, layoutWidth, layoutHeight)
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


class EmotionListAdapter(val gridViewIndex: Int, val layoutWidth: Int, val layoutHeight: Int) : RecyclerView.Adapter<EmotionListAdapter.ViewHolder>() {


    private var emojiSize: Float = -1f
    private var rowHeight: Float = -1f

    init {
        val emojiHeight = layoutHeight.toFloat() / EMOJI_ROWS.toFloat() * 0.6f
        val emojiWidth = layoutWidth.toFloat() / EMOJI_COLUMNS.toFloat() * 0.6f
        rowHeight = layoutHeight.toFloat() / EMOJI_ROWS.toFloat()
        emojiSize = emojiHeight.coerceAtMost(emojiWidth)
    }


    override fun getItemCount(): Int {
        return EMOJI_PER_PAGE + 1
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
            if (position == itemCount - 1) {
                setBackgroundResource(R.drawable.ic_emoji_del)
                return@with
            }
            val index = gridViewIndex * EMOJI_PER_PAGE + position
            if (index >= EmojiLoadManager.getDisplayCount()) {
                throw java.lang.IllegalStateException("index >= EmojiLoadManager.getDisplayCount()")
            }
            EmojiLoadManager.getEmotionUrl(index)?.let {
                val path ="file:///android_asset/$it"
                Glide.with(context)
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this)
            }
        }
    }

    class ViewHolder(val layout: RelativeLayout) : RecyclerView.ViewHolder(layout)
}


class EmotionViewpagerAdapter(val context: Context, val layoutWidth: Int, val layoutHeight: Int) : RecyclerView.Adapter<EmotionViewpagerViewHolder>() {

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
        adapter = EmotionListAdapter(position, layoutWidth, layoutHeight)
    }

}

class EmotionViewpagerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val listView: RecyclerView = itemView.findViewById(R.id.listView)
}

