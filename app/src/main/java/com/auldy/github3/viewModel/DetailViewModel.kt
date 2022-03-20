package com.auldy.github3.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auldy.github3.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class DetailViewModel: ViewModel() {

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }

    private val detailUsers = MutableLiveData<User>()

    fun setDetailUser(usernameid : String){

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$usernameid"
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
                    val item = JSONObject(result)
                    val username = item.getString("login")
                    val name = item.getString("name")
                    val avatar = item.getString("avatar_url")
                    val company = item.getString("company")
                    val location = item.getString("location")
                    val repository = item.getString("public_repos")
                    val followers = item.getString("followers")
                    val following = item.getString("following")
                    val user = User()
                    user.username = username
                    user.name = name
                    user.avatar = avatar
                    user.company = company
                    user.location = location
                    user.repository = repository
                    user.followers = followers
                    user.following = following
                    detailUsers.postValue(user)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    e.printStackTrace()
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
                Log.d("DetailViewModel", errorMessage)
            }
        })
    }
    fun getDetailUsers(): LiveData<User> {
        return detailUsers
    }
}