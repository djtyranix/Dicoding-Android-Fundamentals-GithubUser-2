package com.nixstudio.githubuser2.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.nixstudio.githubuser2.model.UsersItem
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<UsersItem>>()

    fun setUserList(login: String) {
        val listItems = ArrayList<UsersItem>()

        val apiKey = "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"
        val url = "https://api.github.com/search/users?q=${login}"

        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token ${apiKey}")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    Log.d("Login", login)
                    Log.d("Auth Key", apiKey)
                    Log.d("Url", url)
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val currentUser = items.getJSONObject(i)
                        val usersItem = UsersItem()

                        //Assigning JSON Object
                        usersItem.login = currentUser.getString("login")
                        usersItem.followersUrl = currentUser.getString("followers_url")
                        usersItem.type = currentUser.getString("type")
                        usersItem.url = currentUser.getString("url")
                        usersItem.avatarUrl = currentUser.getString("avatar_url")
                        usersItem.id = currentUser.getInt("id")
                        usersItem.gravatarId = currentUser.getString("gravatar_id")

                        listItems.add(usersItem)
                    }

                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })

    }

    fun getUserList(): LiveData<ArrayList<UsersItem>> = listUsers

}