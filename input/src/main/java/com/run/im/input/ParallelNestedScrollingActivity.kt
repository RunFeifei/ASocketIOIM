package com.run.im.input

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL

class ParallelNestedScrollingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewPager = ViewPager2(this).apply {
            layoutParams = matchParent()
            orientation = ORIENTATION_HORIZONTAL

            val list = mutableListOf<EmotionDataSource>()
            list.add(EmotionDataSource())
            list.add(EmotionDataSource())
            list.add(EmotionDataSource())

            adapter = VpAdapter(list)
        }
        setContentView(viewPager)
    }

    class VpAdapter(val listData: List<EmotionDataSource>) : RecyclerView.Adapter<VpAdapter.ViewHolder>() {
        override fun getItemCount(): Int {
            return 4
        }

        @SuppressLint("ResourceType")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val root = inflater.inflate(R.layout.item_nested_recyclerviews, parent, false)
            return ViewHolder(root).apply {
                rv1.setUpRecyclerView()

            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder) {
                itemView.setBackgroundResource(PAGE_COLORS[position % PAGE_COLORS.size])
            }
        }

        private fun RecyclerView.setUpRecyclerView() {
            layoutManager = GridLayoutManager(context, 4)
            adapter = RvAdapter(RecyclerView.HORIZONTAL)
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val rv1: RecyclerView = itemView.findViewById(R.id.listView)
        }
    }

    class RvAdapter(private val orientation: Int) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
        override fun getItemCount(): Int {
            return 40
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val tv = TextView(parent.context)
            tv.layoutParams = matchParent().apply {
                if (orientation == RecyclerView.HORIZONTAL) {
                    width = WRAP_CONTENT
                } else {
                    height = WRAP_CONTENT
                }
            }
            tv.textSize = 20f
            tv.gravity = Gravity.CENTER
            tv.setPadding(20, 55, 20, 55)
            return ViewHolder(tv)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder) {
                tv.text = tv.context.getString(R.string.item_position, adapterPosition)
                tv.setBackgroundResource(CELL_COLORS[position % CELL_COLORS.size])
            }
        }

        class ViewHolder(val tv: TextView) : RecyclerView.ViewHolder(tv)
    }
}

internal fun matchParent(): LayoutParams {
    return LayoutParams(MATCH_PARENT, MATCH_PARENT)
}

internal val PAGE_COLORS = listOf(
    R.color.yellow_300,
    R.color.green_300,
    R.color.teal_300,
    R.color.blue_300
)

internal val CELL_COLORS = listOf(
    R.color.grey_100,
    R.color.grey_300
)
