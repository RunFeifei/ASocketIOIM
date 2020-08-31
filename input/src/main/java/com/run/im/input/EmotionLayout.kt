package com.run.im.input

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

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
        layoutManager = GridLayoutManager(context, 4)
        adapter = ParallelNestedScrollingActivity.RvAdapter(RecyclerView.HORIZONTAL)


    }
}

class EmotionViewpagerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val listView: RecyclerView = itemView.findViewById(R.id.listView)
}