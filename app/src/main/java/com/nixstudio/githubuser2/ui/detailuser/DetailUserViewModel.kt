package com.nixstudio.githubuser2.ui.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.nixstudio.githubuser2.BuildConfig
import com.nixstudio.githubuser2.model.UserDetail
import com.nixstudio.githubuser2.model.UsersItem
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class DetailUserViewModel : ViewModel() {

    val detailUser = MutableLiveData<UserDetail>()
    val listFollowers = MutableLiveData<ArrayList<UsersItem>>()
    val listFollowing = MutableLiveData<ArrayList<UsersItem>>()
    val apiKey = BuildConfig.API_KEY

    fun setUserDetail(login: String) {
        val url = "https://api.github.com/users/${login}"

        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val user = UserDetail()
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                    user.gistsUrl = responseObject.getString("gists_url")
                    user.reposUrl = responseObject.getString("repos_url")
                    user.followingUrl = responseObject.getString("following_url")
                    user.bio = responseObject.getString("bio")
                    user.login = responseObject.getString("login")
                    user.company = responseObject.getString("company")
                    user.id = responseObject.getInt("id")
                    user.publicRepos = responseObject.getInt("public_repos")
                    user.gravatarId = responseObject.getString("gravatar_id")
                    user.followersUrl = responseObject.getString("followers_url")
                    user.publicGists = responseObject.getInt("public_gists")
                    user.url = responseObject.getString("url")
                    user.receivedEventsUrl = responseObject.getString("received_events_url")
                    user.followers = responseObject.getInt("followers")
                    user.avatarUrl = responseObject.getString("avatar_url")
                    user.eventsUrl = responseObject.getString("events_url")
                    user.htmlUrl = responseObject.getString("html_url")
                    user.following = responseObject.getInt("following")
                    user.name = responseObject.getString("name")
                    user.location = responseObject.getString("location")
                    user.nodeId = responseObject.getString("node_id")

                    detailUser.postValue(user)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun setFollowers(login: String) {
        val listItems = ArrayList<UsersItem>()

        val url = "https://api.github.com/users/${login}/followers"
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val items = JSONArray(result)

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

                    listFollowers.postValue(listItems)
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

    fun setFollowing(login: String) {
        val listItems = ArrayList<UsersItem>()

        val url = "https://api.github.com/users/${login}/following"

        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val items = JSONArray(result)

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

                    listFollowing.postValue(listItems)
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

    fun getUserDetail(): LiveData<UserDetail> = detailUser

    fun getFollowers(): LiveData<ArrayList<UsersItem>> = listFollowers

    fun getFollowing(): LiveData<ArrayList<UsersItem>> = listFollowing
}