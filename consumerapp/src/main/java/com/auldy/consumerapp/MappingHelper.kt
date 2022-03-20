package com.auldy.consumerapp

import android.database.Cursor
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.AVATAR
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.COMPANY
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.LOCATION
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.NAME
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.REPOSITORY
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.USERNAME


/**
 * Created By Auldy on 15/04/2021.
 *
 */
object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<Favorite>{
        val users = ArrayList<Favorite>()

        cursor?.apply {
            while (moveToNext()){
                val avatar = getString(getColumnIndex(AVATAR))
                val username = getString(getColumnIndex(USERNAME))
                val name = getString(getColumnIndex(NAME))
                val location = getString(getColumnIndex(LOCATION))
                val company = getString(getColumnIndex(COMPANY))
                val repository = getString(getColumnIndex(REPOSITORY))
                val followers = getString(getColumnIndex(FOLLOWERS))
                val following = getString(getColumnIndex(FOLLOWING))

                users.add(Favorite(
                    avatar, username, name, location, company, repository, followers, following
                ))
            }
        }
        return users
    }

}