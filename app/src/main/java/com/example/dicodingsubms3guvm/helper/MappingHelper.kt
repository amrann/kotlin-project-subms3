package com.example.dicodingsubms3guvm.helper

import android.database.Cursor
import com.example.dicodingsubms3guvm.GithubUser
import com.example.dicodingsubms3guvm.db.DatabaseContract

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<GithubUser> {
        val githubUserList = ArrayList<GithubUser>()

        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns.COLUMN_ID))
                val idUser = getString(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns.COLUMN_ID_USER))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns.COLUMN_USERNAME))
                val ava = getString(getColumnIndexOrThrow(DatabaseContract.GithubUserColumns.COLUMN_AVATAR_URL))
                githubUserList.add(GithubUser(idUser, username, ava, id))
            }
        }
        return githubUserList
    }
}