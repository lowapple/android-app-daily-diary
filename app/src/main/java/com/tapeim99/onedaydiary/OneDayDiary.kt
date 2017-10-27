package com.tapeim99.onedaydiary

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by ndeveat on 2017. 10. 27..
 */

class OneDayDiary : SQLiteOpenHelper {
    constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) :
            super(context, name, factory, version) {

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE OneDayDiary (_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT, color INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {

    }

    fun insert(text: String, color: Int) {
        // 읽고 쓰기가 가능하게 DB 열기
        val db = writableDatabase
        db.execSQL("INSERT INTO OneDayDiary VALUES(null, ${text}, ${color});");
        db.close();
    }

    fun getPage() {
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM OneDayDiary", null);
        while (cursor.moveToNext()) {
            val text = cursor.getString(1)
            val color = cursor.getInt(2)
        }
    }
}