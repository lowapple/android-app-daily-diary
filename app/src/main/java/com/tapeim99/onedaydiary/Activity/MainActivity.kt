package com.tapeim99.onedaydiary.Activity

import android.app.Activity
import android.app.Service
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.tapeim99.onedaydiary.DiaryAdapter
import com.tapeim99.onedaydiary.R
import com.tapeim99.onedaydiary.SQL.OneDayDiary
import com.tapeim99.onedaydiary.Utils.SoftKeyboard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    lateinit var diaryAdapter: DiaryAdapter
    lateinit var oneDayDiary: OneDayDiary

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
        diaryAdapter = DiaryAdapter()

        val datas = oneDayDiary.getPage()
        if (datas.size > 0) {
            empty_view.visibility = View.GONE
            diary_list.visibility = View.VISIBLE

            datas.forEach {
                diaryAdapter.diaries.add(it)
            }
        } else {
            empty_view.visibility = View.VISIBLE
            diary_list.visibility = View.GONE
        }

        search_btn.setOnClickListener {
            activeSearch()
            softKeyboard.openSoftKeyboard()
        }

        clear_btn.setOnClickListener {
            activeMain()
            search_edit.isFocusableInTouchMode = true
            search_edit.requestFocus()
            softKeyboard.closeSoftKeyboard()
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

    fun activeEditor(){

    }
}