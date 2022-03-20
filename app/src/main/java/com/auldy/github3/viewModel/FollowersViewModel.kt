package com.auldy.github3.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auldy.github3.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class FollowersViewModel : ViewModel() {

    companion object {
        private val TAG = FollowersViewModel::class.java.simpleName
    }

    private val followersUsers = MutableLiveData<ArrayList<User>>()

    fun setFollowers(usernameid : String){

        val followerUser = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$usernameid/followers"
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 0ffe9833abaff7e46d8a6c0f0358156a7b53a43c")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val items = JSONArray(result)
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login").toString()
                        val avatar = item.getString("avatar_url").toString()
                        val followers = User()
                        followers.username = username
                        followers.avatar = avatar
                        followerUser.add(followers)
                    }
                    followersUsers.postValue(followerUser)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }
            override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("FollowersViewModel", errorMessage)
            }
        })
    }

    fun getFollowersUsers(): LiveData<ArrayList<User>> {
        return followersUsers
    }

}