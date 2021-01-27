package com.example.dicodingsubms3guvm.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class GithubUserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_githubuser"
            const val COLUMN_USERNAME = "username"
            const val COLUMN_AVATAR_URL = "avatar_url"
            const val COLUMN_ID = "_id"
            const val COLUMN_ID_USER = "id_user"
        }
    }
}