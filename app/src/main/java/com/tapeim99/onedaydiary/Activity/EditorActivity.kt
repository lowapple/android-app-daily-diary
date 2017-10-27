package com.tapeim99.onedaydiary.Activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.tapeim99.onedaydiary.R
import com.tapeim99.onedaydiary.SQL.OneDayDiary

import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.editor_color_button.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class EditorActivity : AppCompatActivity() {

    var diaryColor : Int = 0
    lateinit var oneDayDiary : OneDayDiary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        oneDayDiary = OneDayDiary(this@EditorActivity)

        val color = listOf<Int>(
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor0),
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor1),
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor2),
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor3),
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor4),
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor5),
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor6),
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor7),
                ContextCompat.getColor(this@EditorActivity, R.color.editorColor8)
        )

        color.forEach {
            val view = layoutInflater.inflate(R.layout.editor_color_button, select_color_container, false)
            view.select_color_button.background.setColorFilter(it, PorterDuff.Mode.MULTIPLY)
            view.setOnClickListener { v ->
                Log.d("Color", it.toLong().toString())
                setDiarycolor(it)
            }
            select_color_container.addView(view)
        }

        setDiarycolor(color[0])

        write_btn.setOnClickListener {
            oneDayDiary.insert(contents.text.toString(), diaryColor)

            val intent = intentFor<MainActivity>()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        clear_btn.setOnClickListener {
            finish()
        }
    }

    fun setDiarycolor(color : Int){
        diaryColor = color
        diary_background.background.setColorFilter(diaryColor, PorterDuff.Mode.MULTIPLY)
    }
}