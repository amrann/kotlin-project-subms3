package com.example.dicodingsubms3guvm

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteGithubUser (
var id: Int = 0,
var name: String? = null,
var avatar: String? = null
) : Parcelable