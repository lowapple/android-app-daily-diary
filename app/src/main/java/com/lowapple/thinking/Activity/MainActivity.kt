package com.lowapple.thinking.Activity

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.lowapple.thinking.DiaryAdapter
import com.lowapple.thinking.DiaryModel
import com.lowapple.thinking.SQL.OneDayDiary
import com.lowapple.thinking.R
import com.lowapple.thinking.Utils.SoftKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : Activity() {
    lateinit var diaryAdapter: DiaryAdapter
    lateinit var oneDayDiary: OneDayDiary
    lateinit var diaryList: ArrayList<DiaryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLayout = main_root
        val im = getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager

        val softKeyboard: SoftKeyboard
        softKeyboard = SoftKeyboard(mainLayout, im)
        softKeyboard.setSoftKeyboardCallback(object : SoftKeyboard.SoftKeyboardChanged {

            override fun onSoftKeyboardShow() {
                // Code here
                Log.d("Keyboard", "Show")
            }

            override fun onSoftKeyboardHide() {
                // Code here
                Log.d("Keyboard", "Hide")
            }
        })
        oneDayDiary = OneDayDiary(applicationContext)
        // oneDayDiary.clear()

        diaryAdapter = DiaryAdapter(this@MainActivity)
        diaryAdapter.diaryEvent = object : DiaryAdapter.DiaryEvent {
            override fun diaryClick(holder: DiaryModel) {
                val intent = intentFor<EditorActivity>()
                intent.putExtra("id", holder.id)
                intent.putExtra("contents", holder.text)
                intent.putExtra("color", holder.color)
                intent.putExtra("edit", true)

                startActivity(intent)
            }
        }

        diary_list.layoutManager = LinearLayoutManager(this@MainActivity)
        diary_list.adapter = diaryAdapter

        search_edit.isFocusableInTouchMode = true
        search_btn.setOnClickListener {
            activeSearch()
            softKeyboard.openSoftKeyboard()
            search_edit.requestFocus()
        }

        clear_btn.setOnClickListener {
            activeMain()
            softKeyboard.closeSoftKeyboard()
            load()
        }

        write_btn.setOnClickListener {
            startActivity(intentFor<EditorActivity>())
        }

        search_edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                val data = diaryList.filter { it.text.contains(p0) }
                diaryAdapter.diaries = ArrayList<DiaryModel>(data)
                diaryAdapter.notifyDataSetChanged()
            }
        })
    }

    fun load() {
        diaryList = oneDayDiary.getPage()
        if (diaryList.size > 0) {
            empty_view.visibility = View.GONE
            diary_list.visibility = View.VISIBLE
            diaryAdapter.diaries = diaryList
            diaryAdapter.notifyDataSetChanged()
        } else {
            empty_view.visibility = View.VISIBLE
            diary_list.visibility = View.GONE
        }
    }

    fun activeMain() {
        main_top_view.visibility = View.VISIBLE
        search_top_view.visibility = View.GONE
    }

    fun activeSearch() {
        main_top_view.visibility = View.GONE
        search_top_view.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        load()
    }
}