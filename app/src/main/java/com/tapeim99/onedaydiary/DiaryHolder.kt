package com.tapeim99.onedaydiary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.diary_item.view.*

/**
 * Created by ndeveat on 2017. 10. 27..
 */

class DiaryHolder : RecyclerView.ViewHolder {
    val background : LinearLayout
    val text : TextView

    constructor(view: View) : super(view) {
        background = view.diary_background
        text = view.diary_text
    }

    fun setBackground(color : Int){
        background.setBackgroundColor(color)
    }

    fun setText(text : String){
        this.text.text = text
    }
}