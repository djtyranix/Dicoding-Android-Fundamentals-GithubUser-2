package com.nixstudio.githubuser2.ui.detailuser.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nixstudio.githubuser2.databinding.FragmentFollowingBinding
import com.nixstudio.githubuser2.ui.detailuser.DetailUserViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailUserViewModel by activityViewModels()
    private lateinit var viewAdapter: FollowingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        viewAdapter = FollowingListAdapter()
        viewAdapter.notifyDataSetChanged()

        binding.followingRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
        }

        showLoading(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getFollowing().observe(viewLifecycleOwner, { usersItem ->
            if (usersItem != null) {
                viewAdapter.setData(usersItem)
                showLoading(false)
            }
        })
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}