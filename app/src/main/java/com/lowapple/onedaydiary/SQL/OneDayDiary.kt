package com.lowapple.onedaydiary.SQL

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.lowapple.onedaydiary.DiaryModel
import java.util.*

/**
 * Created by lowapple on 2017. 10. 27..
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

    fun delete(id : Int){
        val db = writableDatabase
        val sql = "DELETE FROM OneDayDiaryData WHERE _id=${id};"
        db.execSQL(sql)
        db.close()
    }

    fun update(id : Int, text : String, color : Int){
        val db = writableDatabase
        val sql = "UPDATE OneDayDiaryData SET text='${text}',color=${color} WHERE _id=${id};"
        db.execSQL(sql)
        db.close()
    }

    fun size() : Int {
        val db = writableDatabase
        val sql = "SELECT * FROM OneDayDiaryData"
        val cursor = db.rawQuery(sql, null)
        return cursor.columnCount
    }

    fun getPage(): ArrayList<DiaryModel> {
        val db = readableDatabase
        val array = ArrayList<DiaryModel>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM OneDayDiaryData", null);
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val text = cursor.getString(1)
            val color = cursor.getInt(2)

            array.add(DiaryModel(id, text, color))
        }
        // 뒤집기
        array.reverse()
        return array
    }
}