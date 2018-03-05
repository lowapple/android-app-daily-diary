package com.lowapple.onedaydiary.Activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import com.lowapple.onedaydiary.SQL.OneDayDiary
import com.lowapple.onedaydiary.R

import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.editor_color_button.view.*
import org.jetbrains.anko.intentFor

class EditorActivity : AppCompatActivity() {

    var diaryColor: Int = 0
    lateinit var oneDayDiary: OneDayDiary

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
                setDiaryBackground(it)
            }
            select_color_container.addView(view)
        }

        // Write Button
        write_btn.setOnClickListener {
            oneDayDiary.insert(contents.text.toString(), diaryColor)

            val intent = intentFor<MainActivity>()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // Clear Button
        clear_btn.setOnClickListener {
            finish()
        }

        delete_btn.visibility = View.INVISIBLE

        try {
            val id = intent.extras.getInt("id", oneDayDiary.size())
            val contents = intent.extras.getString("contents")
            val background = intent.extras.getInt("color", 0)
            val isEdit = intent.extras.getBoolean("edit", false)

            setDiaryBackground(background)
            setDiaryContent(contents)

            if (isEdit)
                delete_btn.visibility = View.VISIBLE

            // Delete Button
            delete_btn.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(this@EditorActivity)
                dialogBuilder.setMessage("정말 삭제 하시겠습니까?")
                dialogBuilder.setCancelable(false)
                dialogBuilder.setPositiveButton("삭제", DialogInterface.OnClickListener { dialogInterface, i ->
                    oneDayDiary.delete(id)
                    finish()
                })
                dialogBuilder.setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
                dialogBuilder.show()
            }
        } catch (e: Exception) {

        }
    }

    fun setDiaryBackground(color: Int) {
        diaryColor = color
        diary_background.background.setColorFilter(diaryColor, PorterDuff.Mode.MULTIPLY)
    }

    fun setDiaryContent(content: String) {
        contents.text = SpannableStringBuilder(content)
    }
}