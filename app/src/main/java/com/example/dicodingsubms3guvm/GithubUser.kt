package com.example.dicodingsubms3guvm

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
//data class GithubUser (
//    var id: Int = 0,
//    var name: String,
//    var avatar: String
//) : Parcelable

@Parcelize
data class GithubUser (
    var idUser: String? = null,
    var name: String? = null,
    var avatar: String? = null,
    var id: Int = 0
) : Parcelable

