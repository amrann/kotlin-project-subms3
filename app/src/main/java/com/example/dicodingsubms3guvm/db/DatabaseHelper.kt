package com.example.dicodingsubms3guvm.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dicodingsubms3guvm.db.DatabaseContract.GithubUserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfavoritegithubuser"
        private const val DATABASE_VERSION = 2
        private val SQL_CREATE_TABLE_GITHUBUSER = "CREATE TABLE $TABLE_NAME" +
                "(${DatabaseContract.GithubUserColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DatabaseContract.GithubUserColumns.COLUMN_ID_USER} TEXT NOT NULL," +
                "${DatabaseContract.GithubUserColumns.COLUMN_USERNAME} TEXT NOT NULL," +
                "${DatabaseContract.GithubUserColumns.COLUMN_AVATAR_URL} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_GITHUBUSER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}