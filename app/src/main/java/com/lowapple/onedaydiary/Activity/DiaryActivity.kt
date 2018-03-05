package com.lowapple.onedaydiary.Activity

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import com.lowapple.onedaydiary.SQL.OneDayDiary
import com.lowapple.onedaydiary.R

import kotlinx.android.synthetic.main.activity_diary.*
import kotlinx.android.synthetic.main.editor_color_button.view.*
import org.jetbrains.anko.intentFor

class DiaryActivity : AppCompatActivity() {
    var diaryColor : Int = 0
    lateinit var oneDayDiary : OneDayDiary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        oneDayDiary = OneDayDiary(this@DiaryActivity)

        val color = listOf<Int>(
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor0),
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor1),
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor2),
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor3),
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor4),
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor5),
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor6),
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor7),
                ContextCompat.getColor(this@DiaryActivity, R.color.editorColor8)
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

        val id = intent.extras.getInt("id")
        val contents = intent.extras.getString("contents")
        val background = intent.extras.getInt("color")

        this.contents.text = SpannableStringBuilder(contents)

        setDiarycolor(background)

        delete_btn.setOnClickListener {
            oneDayDiary.delete(id)

            val intent = intentFor<MainActivity>()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        clear_btn.setOnClickListener {
            oneDayDiary.update(id, this.contents.text.toString(), background)

            finish()
        }
    }

    fun setDiarycolor(color : Int){
        diaryColor = color
        diary_background.background.setColorFilter(diaryColor, PorterDuff.Mode.MULTIPLY)
    }
}