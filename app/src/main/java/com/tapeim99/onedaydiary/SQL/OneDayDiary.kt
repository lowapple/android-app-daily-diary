package com.tapeim99.onedaydiary.SQL

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.tapeim99.onedaydiary.DiaryModel
import java.util.*

/**
 * Created by ndeveat on 2017. 10. 27..
 */

class OneDayDiary : SQLiteOpenHelper {
    constructor(context: Context) : super(context, "OneDayDiary.db", null, 1) {

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE OneDayDiaryData (_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT, color INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {

    }

    fun insert(text: String, color: Int) {
        // 읽고 쓰기가 가능하게 DB 열기
        val db = writableDatabase
        val sql = "INSERT INTO OneDayDiaryData VALUES(null, '${text}', ${color});"
        Log.d("SQL", sql)

        db.execSQL(sql)
        db.close()
    }

    fun getPage(): ArrayList<DiaryModel> {
        val db = readableDatabase
        val array = ArrayList<DiaryModel>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM OneDayDiaryData", null);
        while (cursor.moveToNext()) {
            val text = cursor.getString(1)
            val color = cursor.getInt(2)

            array.add(DiaryModel(text, color))
        }
        return array
    }
}