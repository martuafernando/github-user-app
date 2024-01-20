package com.example.githubuserapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.response.UserDetail
import com.example.githubuserapp.databinding.FragmentUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val FOLLOWER_TAB_INDEX = 0
        private const val FOLLOWING_TAB_INDEX = 1

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_follower,
            R.string.tab_text_following
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        val userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UserDetailViewModel::class.java]

        userDetailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        userDetailViewModel.user.observe(viewLifecycleOwner) {
            user -> setUserData(user)
        }

        val username = UserDetailFragmentArgs
            .fromBundle(arguments as Bundle)
            .username

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = "${resources.getString(TAB_TITLES[position])} (0)"
        }.attach()

        userDetailViewModel.getUserDetail(username)
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUserData(user: UserDetail?) {
        if (user != null) {
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.tvUserPhoto)
            binding.tvUserName.text = user.name ?: "No Name"

            val username = "@${user.login}"
            binding.tvUserUsername.text = username
            binding.tvUserLocation.text = user.location ?: "Location Unknown"

            val tabs: TabLayout = binding.tabs
            tabs.getTabAt(FOLLOWER_TAB_INDEX)?.text = "${resources.getString(TAB_TITLES[FOLLOWER_TAB_INDEX])} (${user.followers})"
            tabs.getTabAt(FOLLOWING_TAB_INDEX)?.text = "${resources.getString(TAB_TITLES[FOLLOWING_TAB_INDEX])} (${user.following})"
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}