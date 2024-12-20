package com.example.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.RelatedUser
import com.example.githubuserapp.databinding.FragmentRelatedUserBinding

const val ARG_POSITION = "POSITION"
const val ARG_USERNAME = "USERNAME"
class RelatedUserFragment : Fragment() {
    private var _binding: FragmentRelatedUserBinding? = null
    private val binding get() = _binding!!
    private var position: Int? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelatedUserBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val relatedUserViewModel: RelatedUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RelatedUserViewModel::class.java]

        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)

        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.addItemDecoration(itemDecoration)

        relatedUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (username != null) {
            if (position == 1) {
                relatedUserViewModel.listFollower.observe(viewLifecycleOwner) {
                    follower -> setUserRelated(follower)
                }
                relatedUserViewModel.getUserFollowers(username as String)
            } else {
                relatedUserViewModel.listFollowing.observe(viewLifecycleOwner) {
                    following -> setUserRelated(following)
                }
                relatedUserViewModel.getUserFollowings(username as String)
            }
        }

        return binding.root
    }

    private fun setUserRelated(users: List<RelatedUser>) {
        val adapter = RelatedUserAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        /**
         *
         * @param position Parameter 1.
         * @param username Parameter 2.
         * @return A new instance of fragment RelatedUserFragment.
         */
        @JvmStatic
        fun newInstance(position: Int, username: String) =
            RelatedUserFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putString(ARG_USERNAME, username)
                }
            }
    }
}