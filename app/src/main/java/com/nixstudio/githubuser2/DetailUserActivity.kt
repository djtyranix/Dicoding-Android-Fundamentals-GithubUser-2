package com.nixstudio.githubuser2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.nixstudio.githubuser2.ui.detailuser.DetailUserFragment
import com.nixstudio.githubuser2.ui.detailuser.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {

    private val viewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_user_activity)

        val args: DetailUserActivityArgs by navArgs()
        val user = args.userData
        val username = user.login

        viewModel.setUserDetail(username)

        val mFragmentManager = supportFragmentManager
        val mDetailUserFragment = DetailUserFragment()

        mFragmentManager.beginTransaction()
            .replace(R.id.detail_user_fragment_container, mDetailUserFragment)
            .commit()
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }
}