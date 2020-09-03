package com.run.im.input.special

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.run.im.input.Config
import com.run.im.input.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_special.*

/**
 * Created by PengFeifei on 2020/9/03.
 */

private val List_Special_Items = arrayListOf(
    Pair(R.string.picture, R.mipmap.ic_func_pic),
    Pair(R.string.video, R.mipmap.ic_func_video),
    Pair(R.string.shot, R.mipmap.ic_func_shot),
    Pair(R.string.file, R.mipmap.ic_func_file),
    Pair(R.string.location, R.mipmap.ic_func_location)
)


class SpecialPanelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE) {
            post {
                init()
            }
        }
    }

    private fun init() {
        layoutManager = GridLayoutManager(context, Config.SPECIAL_COLUMNS, VERTICAL, false)
        adapter = SpecialPanelAdapter(List_Special_Items)
    }
}

class SpecialPanelAdapter(val list: List<Pair<Int, Int>>) : RecyclerView.Adapter<SpecialPanelAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_special, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            imgItem.setImageResource(list[position].second)
            textTip.setText(list[position].first)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}