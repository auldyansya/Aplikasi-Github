package com.auldy.github3.db

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created By Auldy on 15/04/2021.
 *
 */
internal class DatabaseContract {

    companion object {
        const val AUTHORITY = "com.auldy.github3"
        const val SCHEME = "content"
    }

    internal class UserColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val AVATAR = "avatar"
            const val USERNAME = "username"
            const val NAME = "name"
            const val LOCATION = "location"
            const val COMPANY = "company"
            const val REPOSITORY = "repository"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

}