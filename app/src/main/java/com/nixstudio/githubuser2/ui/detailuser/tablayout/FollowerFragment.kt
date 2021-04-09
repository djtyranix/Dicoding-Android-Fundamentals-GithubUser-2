package com.nixstudio.githubuser2.ui.detailuser.tablayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nixstudio.githubuser2.R
import com.nixstudio.githubuser2.databinding.FragmentFollowerBinding
import com.nixstudio.githubuser2.model.UsersItem
import com.nixstudio.githubuser2.ui.detailuser.DetailUserViewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailUserViewModel by activityViewModels()
    private lateinit var viewAdapter: FollowerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)

        viewAdapter = FollowerListAdapter()
        viewAdapter.notifyDataSetChanged()

        binding.followersRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
        }

        showLoading(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getFollowers().observe(viewLifecycleOwner, { usersItem ->
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