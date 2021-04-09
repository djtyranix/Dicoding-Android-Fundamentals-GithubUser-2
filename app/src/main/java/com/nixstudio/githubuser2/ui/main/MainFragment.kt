package com.nixstudio.githubuser2.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nixstudio.githubuser2.R
import com.nixstudio.githubuser2.databinding.MainFragmentBinding
import com.nixstudio.githubuser2.model.UsersItem

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    lateinit var viewAdapter: UserListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        viewAdapter = UserListAdapter()
        viewAdapter.notifyDataSetChanged()

        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
        }

        binding.noUser.text = resources.getString(R.string.welcome_message)

        return binding.root
    }

    fun searchUser(userLogin: String?) {
        if (userLogin != null) {
            showLoading(true)
            viewModel.setUserList(userLogin)
        }
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.noUser.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getUserList().observe(viewLifecycleOwner, { usersItem ->
            if (usersItem != null) {
                if (usersItem.size > 0) {
                    binding.noUser.visibility = View.GONE
                    binding.userRecyclerView.visibility = View.VISIBLE
                } else {
                    binding.noUser.text = resources.getString(R.string.user_not_found)
                    binding.noUser.visibility = View.VISIBLE
                    binding.userRecyclerView.visibility = View.GONE
                }

                viewAdapter.setData(usersItem)
                showLoading(false)
            }
        })

        viewAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersItem) {
                showItemDetail(data)
            }
        })
    }

    private fun showItemDetail(data: UsersItem) {
        val toDetailUserActivity =
            MainFragmentDirections.actionMainFragmentToDetailUserActivity(data)
        view?.findNavController()?.navigate(toDetailUserActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}