package com.nixstudio.githubuser2.ui.detailuser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nixstudio.githubuser2.DetailUserActivity
import com.nixstudio.githubuser2.DetailUserActivityArgs
import com.nixstudio.githubuser2.R
import com.nixstudio.githubuser2.databinding.DetailUserFragmentBinding
import com.nixstudio.githubuser2.databinding.MainFragmentBinding
import com.nixstudio.githubuser2.model.UserDetail
import com.nixstudio.githubuser2.model.UsersItem
import de.hdodenhof.circleimageview.CircleImageView

class DetailUserFragment : Fragment() {

    private var _binding: DetailUserFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailUserViewModel by activityViewModels()
    lateinit var imgPhoto : CircleImageView
    lateinit var tvUsername : TextView
    lateinit var tvName : TextView
    lateinit var tvFollowersFollowing : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailUserFragmentBinding.inflate(inflater, container, false)
        val currentActivity = activity as DetailUserActivity

        imgPhoto = binding.imgUserPhoto
        tvUsername = binding.tvUserUsername
        tvName = binding.tvName
        tvFollowersFollowing = binding.tvFollowersFollowing

        currentActivity.setActionBarTitle("Loading User")
        setVisibility(false)
        showLoading(true)

        viewModel.getUserDetail().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                setData(user)
                currentActivity.setActionBarTitle(user.login)
                showLoading(false)
                setVisibility(true)
            }
        })

        return binding.root
    }

    fun setData(data: UserDetail) {
        Glide.with(this)
            .load(data.avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(imgPhoto)

        tvUsername.text = data.login
        tvName.text = data.name
        tvFollowersFollowing.text = getString(R.string.followers_following, data.followers, data.following)
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    fun setVisibility(state: Boolean) {
        if (state) {
            imgPhoto.visibility = View.VISIBLE
            tvUsername.visibility = View.VISIBLE
            tvName.visibility = View.VISIBLE
            tvFollowersFollowing.visibility = View.VISIBLE
        } else {
            imgPhoto.visibility = View.GONE
            tvUsername.visibility = View.GONE
            tvName.visibility = View.GONE
            tvFollowersFollowing.visibility = View.GONE
        }
    }

}