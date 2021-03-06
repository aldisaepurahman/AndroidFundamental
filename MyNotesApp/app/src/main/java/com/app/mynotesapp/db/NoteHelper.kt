package com.app.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.app.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import android.database.SQLException
import com.app.mynotesapp.db.DatabaseContract.NoteColumns.Companion._ID
import kotlin.jvm.Throws

class NoteHelper(context: Context) {
    private var dataBaseHelper : DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    /*pembuatan koneksi/inisialisasi database dan juga singleton class*/
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
    }

    /*membuka dan menutup koneksi ke database (supaya tidak membuat memory leak)*/
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen) database.close()
    }

    /*query mengambil seluruh data*/
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    /*ambil data berdasarkan ID*/
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    /*CRUD method*/
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}