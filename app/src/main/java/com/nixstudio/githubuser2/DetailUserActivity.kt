package com.nixstudio.githubuser2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.nixstudio.githubuser2.ui.detailuser.DetailUserFragment
import com.nixstudio.githubuser2.ui.detailuser.DetailUserViewModel
import com.nixstudio.githubuser2.ui.detailuser.TablayoutContainerFragment

class DetailUserActivity : AppCompatActivity() {

    private val viewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_user_activity)

        val args: DetailUserActivityArgs by navArgs()
        val user = args.userData
        val username = user.login

        viewModel.setFollowers(username)
        viewModel.setFollowing(username)
        viewModel.setUserDetail(username)

        if (savedInstanceState == null) {
            val mFragmentManager = supportFragmentManager
            val mDetailUserFragment = DetailUserFragment()
            val mTablayoutContainerFragment = TablayoutContainerFragment()

            mFragmentManager.beginTransaction()
                .replace(R.id.detail_user_fragment_container, mDetailUserFragment)
                .commit()

            mFragmentManager.beginTransaction()
                .replace(R.id.tablayout_fragment_container, mTablayoutContainerFragment)
                .commit()
        }
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }
}