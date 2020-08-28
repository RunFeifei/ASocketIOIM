package com.run.im.input

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.run.im.input.Config.Companion.EMOJI_COLUMNS
import kotlinx.android.synthetic.main.item_emotion.*
import zlc.season.yasha.grid

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
        val list = mutableListOf<EmotionDataSource>()
        list.add(EmotionDataSource())
        list.add(EmotionDataSource())
        list.add(EmotionDataSource())
        emotionViewPager.adapter = EmotionViewpagerAdapter(context, list)
    }

}

class EmotionViewpagerAdapter(val context: Context, val listData: List<EmotionDataSource>) : RecyclerView.Adapter<EmotionViewpagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewpagerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_emotion_viewpager, parent, false)
        return EmotionViewpagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: EmotionViewpagerViewHolder, position: Int) {
        val listView: RecyclerView = holder.view.findViewById(R.id.listView)
        listView.grid(listData[position]) {
            renderItem<EmotionItem> {
                res(R.layout.item_emotion)
                onBind {
                    textView.text = data.data + "-" + position
                }
                spanCount(EMOJI_COLUMNS)
            }
        }
    }
}

class EmotionViewpagerViewHolder(val view: View) : RecyclerView.ViewHolder(view);