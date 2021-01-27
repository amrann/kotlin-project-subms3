package com.example.dicodingsubms3guvm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.dicodingsubms3guvm.db.DatabaseContract.GithubUserColumns.Companion.COLUMN_ID
import com.example.dicodingsubms3guvm.db.DatabaseContract.GithubUserColumns.Companion.COLUMN_ID_USER
import com.example.dicodingsubms3guvm.db.DatabaseContract.GithubUserColumns.Companion.COLUMN_USERNAME
import com.example.dicodingsubms3guvm.db.DatabaseContract.GithubUserColumns.Companion.TABLE_NAME
import java.sql.SQLException

class GithubUserHelper(context: Context) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: GithubUserHelper? = null

        fun getInstance(context: Context): GithubUserHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: GithubUserHelper(context)
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$COLUMN_ID_USER = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$COLUMN_ID_USER = '$id'", null)
    }
}
