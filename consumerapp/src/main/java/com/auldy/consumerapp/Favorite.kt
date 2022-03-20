package com.auldy.consumerapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created By Auldy on 15/04/2021.
 *
 */
@Parcelize
data class Favorite (
    var avatar: String? = "",
    var username: String? = "",
    var name: String? = "",
    var location: String? = "",
    var company: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = ""
) : Parcelable