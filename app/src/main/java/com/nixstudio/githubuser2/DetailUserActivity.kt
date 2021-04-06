package com.nixstudio.githubuser2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nixstudio.githubuser2.ui.detailuser.DetailUserFragment
import com.nixstudio.githubuser2.ui.detailuser.FollowerFragment

class DetailUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_user_activity)

        val mFragmentManager = supportFragmentManager
        val mDetailUserFragment = DetailUserFragment()
        val mFollowerFragment = FollowerFragment()

        mFragmentManager.beginTransaction()
            .replace(R.id.detail_user_fragment_container, mDetailUserFragment)
            .commit()

        mFragmentManager.beginTransaction()
            .replace(R.id.follower_fragment_container, mFollowerFragment)
            .commit()
    }
}