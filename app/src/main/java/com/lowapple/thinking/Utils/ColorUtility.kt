package com.lowapple.thinking.Utils

import android.content.Context
import android.support.v4.content.ContextCompat
import com.lowapple.thinking.R

/**
 * Created by junsu on 2018-03-06.
 */

class ColorUtility(context : Context) {
    val color : List<Int> = listOf<Int>(
            ContextCompat.getColor(context, R.color.editorColor0),
            ContextCompat.getColor(context, R.color.editorColor1),
            ContextCompat.getColor(context, R.color.editorColor2),
            ContextCompat.getColor(context, R.color.editorColor3),
            ContextCompat.getColor(context, R.color.editorColor4),
            ContextCompat.getColor(context, R.color.editorColor5),
            ContextCompat.getColor(context, R.color.editorColor6),
            ContextCompat.getColor(context, R.color.editorColor7),
            ContextCompat.getColor(context, R.color.editorColor8)
    )

    fun getColor(n : Int) : Int = color[n]

    fun getColors() : List<Int> = color
}