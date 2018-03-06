package com.lowapple.onedaydiary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lowapple.onedaydiary.R
import com.lowapple.onedaydiary.Utils.ColorUtility
import java.util.*

/**
 * Created by lowapple on 2017. 10. 27..
 */

class DiaryAdapter(context: Context) : RecyclerView.Adapter<DiaryHolder>() {
    interface DiaryEvent {
        fun diaryClick(holder: DiaryModel)
    }

    var diaries = ArrayList<DiaryModel>()
    var diaryEvent: DiaryEvent? = null
    val color = ColorUtility(context)

    override fun getItemCount(): Int = diaries.size
    override fun onBindViewHolder(holder: DiaryHolder, position: Int) {
        val diary = diaries[position]

        if (diary.color >= 0)
            holder.setBackground(color.getColor(diary.color))
        else
            holder.setBackground(0)
        holder.setText(diary.text)
        holder.background.setOnClickListener {
            diaryEvent?.diaryClick(diary)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_item, parent, false)
        val diary = DiaryHolder(view)

        return diary
    }
}