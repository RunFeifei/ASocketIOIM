package com.run.im.input.emoji

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.run.im.input.R

/**
 * Created by PengFeifei on 2020/8/28.
 */
class EmotionLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    private var emotionViewPager: ViewPager2

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_emotion, this)
        emotionViewPager = findViewById(R.id.emotionViewPager)
        initViewPager()
    }

    private fun initViewPager() {
        emotionViewPager.adapter = EmotionViewpagerAdapter(context)
    }
}


class EmotionListAdapter() : RecyclerView.Adapter<EmotionListAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return 400
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tv = TextView(parent.context)
        tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        tv.textSize = 20f
        tv.gravity = Gravity.CENTER
        tv.setPadding(20, 55, 20, 55)
        return ViewHolder(tv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            tv.text = tv.context.getString(R.string.item_position, adapterPosition)
        }
    }

    class ViewHolder(val tv: TextView) : RecyclerView.ViewHolder(tv)
}


class EmotionViewpagerAdapter(val context: Context) : RecyclerView.Adapter<EmotionViewpagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewpagerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_emotion_viewpager, parent, false)
        return EmotionViewpagerViewHolder(view).apply {
            listView.setUpRecyclerView()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: EmotionViewpagerViewHolder, position: Int) {

    }

    private fun RecyclerView.setUpRecyclerView() {
        layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
        adapter = EmotionListAdapter()
    }

}

class EmotionViewpagerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val listView: RecyclerView = itemView.findViewById(R.id.listView)
}

