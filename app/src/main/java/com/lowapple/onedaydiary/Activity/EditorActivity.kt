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
import com.lowapple.onedaydiary.Utils.ColorUtility

import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.editor_color_button.view.*
import org.jetbrains.anko.intentFor

class EditorActivity : AppCompatActivity() {

    var diaryColor: Int = 0
    var diaryColorIndex: Int = 0
    lateinit var oneDayDiary: OneDayDiary
    var isEdit = false
    var id = 0
    lateinit var color : ColorUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        oneDayDiary = OneDayDiary(this@EditorActivity)
        color = ColorUtility(this@EditorActivity)

        color.getColors().forEachIndexed { index, i ->
            val view = layoutInflater.inflate(R.layout.editor_color_button, select_color_container, false)
            view.select_color_button.background.setColorFilter(i, PorterDuff.Mode.MULTIPLY)
            view.setOnClickListener { v ->
                setDiaryBackground(index)
            }
            select_color_container.addView(view)
        }

        // Write Button
        write_btn.setOnClickListener {
            val text = contents.text.toString()
            if (!isEdit)
                oneDayDiary.insert(text, diaryColorIndex)
            else
                oneDayDiary.update(id, text, diaryColorIndex)
            finish()
        }

        // Clear Button
        clear_btn.setOnClickListener {
            finish()
        }

        delete_btn.visibility = View.INVISIBLE

        setDiaryBackground(0)
        try {
            isEdit = intent.extras.getBoolean("edit", false)

            val id = intent.extras.getInt("id")
            val contents = intent.extras.getString("contents")
            val background = intent.extras.getInt("color", 0)

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
        diaryColor = this.color.getColor(color)
        diaryColorIndex = color
        diary_background.background.setColorFilter(diaryColor, PorterDuff.Mode.MULTIPLY)
    }

    fun setDiaryContent(content: String) {
        contents.text = SpannableStringBuilder(content)
    }
}