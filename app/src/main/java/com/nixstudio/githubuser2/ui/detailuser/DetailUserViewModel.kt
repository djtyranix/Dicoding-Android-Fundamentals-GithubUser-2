package com.nixstudio.githubuser2.ui.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.nixstudio.githubuser2.model.UserDetail
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailUserViewModel : ViewModel() {

    val detailUser = MutableLiveData<UserDetail>()

    fun setUserDetail(login: String) {
        val apiKey = "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"
        val url = "https://api.github.com/users/${login}"

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

    fun getUserDetail(): LiveData<UserDetail> = detailUser
}