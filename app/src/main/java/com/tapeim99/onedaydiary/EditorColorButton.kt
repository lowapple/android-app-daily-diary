package com.tapeim99.onedaydiary

import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.editor_color_button.view.*

/**
 * Created by ndeveat on 2017. 10. 27..
 */

class EditorColorButton : FrameLayout {

    lateinit var selectColorButton: FrameLayout

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs) {
        init()
        getAttrs(attrs, defStyle)
    }

    fun init() {
        val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.editor_color_button, this, false)
        addView(view)

        selectColorButton = view.select_color_button
    }

    fun getAttrs(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.EditorColorButton)
        setTypedArray(typeArray)
    }

    fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.EditorColorButton, defStyle, 0)
        setTypedArray(typeArray)
    }

    fun setTypedArray(typedArray: TypedArray) {
        val buttonColor = typedArray.getColor(R.styleable.EditorColorButton_color, 0)

        selectColorButton.select_color_button.background.setColorFilter(buttonColor, PorterDuff.Mode.MULTIPLY)
    }
}