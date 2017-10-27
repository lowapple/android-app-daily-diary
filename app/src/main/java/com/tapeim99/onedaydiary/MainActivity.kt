package com.tapeim99.onedaydiary

import android.app.Service
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout


class MainActivity : AppCompatActivity() {

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
    }
}
