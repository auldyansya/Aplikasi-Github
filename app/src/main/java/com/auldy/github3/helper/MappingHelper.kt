package com.auldy.github3.helper

import android.database.Cursor
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.NAME
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.REPOSITORY
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.auldy.github3.model.User

/**
 * Created By Auldy on 15/04/2021.
 *
 */
object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User>{
        val users = ArrayList<User>()

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

                users.add(User(
                    avatar, username, name, location, company, repository, followers, following
                ))
            }
        }
        return users
    }

}