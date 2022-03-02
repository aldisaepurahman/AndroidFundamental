package com.app.mynotesapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.app.mynotesapp.db.DatabaseContract.*
import com.app.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME

/*kelas untuk mengkonfigurasi pembuatan dan penghapusan database*/
internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbnoteapp"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                "(${NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${NoteColumns.TITLE} TEXT NOT NULL," +
                "${NoteColumns.DESCRIPTION} TEXT NOT NULL," +
                "${NoteColumns.DATE} TEXT NOT NULL)"
    }
    /*membuat database*/
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    /*mengubah struktur database*/
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}