package com.tapeim99.onedaydiary

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.*

/**
 * Created by ndeveat on 2017. 10. 27..
 */

class DiaryAdapter : RecyclerView.Adapter<DiaryHolder>() {
    val diaries = ArrayList<DiaryModel>()

    override fun getItemCount(): Int = diaries.size

    override fun onBindViewHolder(holder: DiaryHolder?, position: Int) {
        val diary = diaries[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_item, parent, false)
        val diary = DiaryHolder(view)

        return diary
    }
}