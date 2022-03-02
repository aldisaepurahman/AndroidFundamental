package com.app.mynotesapp.db

import android.provider.BaseColumns

/*seluruh data konfigurasi database disimpan disini, seperti
* nama table beserta atributnya*/
internal class DatabaseContract {
    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
        }
    }
}