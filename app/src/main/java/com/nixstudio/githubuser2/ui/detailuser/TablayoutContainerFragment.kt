package com.nixstudio.githubuser2.ui.detailuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nixstudio.githubuser2.R
import com.nixstudio.githubuser2.databinding.DetailUserFragmentBinding
import com.nixstudio.githubuser2.databinding.TablayoutContainerFragmentBinding

class TablayoutContainerFragment : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.followers,
                R.string.following
        )

        private const val TAB_STATE = "tab_state"
    }

    private var _binding: TablayoutContainerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TablayoutContainerFragmentBinding.inflate(inflater, container, false)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLayout

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        if (savedInstanceState != null) {
            val currentPage = savedInstanceState.getInt(TAB_STATE, -1)
            if (currentPage > -1) {
                viewPager.currentItem = currentPage
            }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_STATE, viewPager.currentItem)
    }
}