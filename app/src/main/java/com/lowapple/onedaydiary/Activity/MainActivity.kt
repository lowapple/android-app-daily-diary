package com.lowapple.onedaydiary.Activity

import android.app.Activity
import android.app.Service
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.lowapple.onedaydiary.DiaryAdapter
import com.lowapple.onedaydiary.DiaryModel
import com.lowapple.onedaydiary.SQL.OneDayDiary
import com.lowapple.onedaydiary.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : Activity() {
    lateinit var diaryAdapter: DiaryAdapter
    lateinit var oneDayDiary: OneDayDiary

    lateinit var diaryList : ArrayList<DiaryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneDayDiary = OneDayDiary(applicationContext)
        diaryAdapter = DiaryAdapter()
        diaryAdapter.diaryEvent = object : DiaryAdapter.DiaryEvent {
            override fun diaryClick(holder: DiaryModel) {
                val intent = intentFor<DiaryActivity>()
                intent.putExtra("id", holder.id)
                intent.putExtra("contents", holder.text)
                intent.putExtra("color", holder.color)

                startActivity(intent)
            }
        }

        diary_list.layoutManager = LinearLayoutManager(this@MainActivity)
        diary_list.adapter = diaryAdapter

        load()

        search_edit.isFocusableInTouchMode = true
        search_btn.setOnClickListener {
            activeSearch()
            search_edit.requestFocus()
        }

        clear_btn.setOnClickListener {
            activeMain()
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

    fun load(){
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

    fun activeMain(){
        main_top_view.visibility = View.VISIBLE
        search_top_view.visibility = View.GONE
    }

    fun activeSearch(){
        main_top_view.visibility = View.GONE
        search_top_view.visibility = View.VISIBLE
    }
}