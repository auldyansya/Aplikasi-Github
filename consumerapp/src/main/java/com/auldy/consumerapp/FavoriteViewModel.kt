package com.auldy.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.auldy.consumerapp.DatabaseContract.UserColumns.Companion.CONTENT_URI

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val list = MutableLiveData<ArrayList<Favorite>>()

    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query (
            CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        list.postValue(listConverted)
    }

    fun getFavoriteUser(): LiveData<ArrayList<Favorite>>? {
        return list
    }
}