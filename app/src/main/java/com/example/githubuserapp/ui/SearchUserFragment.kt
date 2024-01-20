package com.example.githubuserapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.UserItem
import com.example.githubuserapp.databinding.FragmentSearchUserBinding

class SearchUserFragment : Fragment() {

    private var _binding: FragmentSearchUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SearchUserViewModel::class.java]

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        mainViewModel.listUser.observe(viewLifecycleOwner) {
                listUser -> setUserData(listUser)
        }

        with(binding) {
            binding.rvUsers.layoutManager = layoutManager
            binding.rvUsers.addItemDecoration(itemDecoration)

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.searchUser(searchView.text.toString())
                    false
                }
        }
        return binding.root

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUserData(users: List<UserItem>) {
        val adapter = UserAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickedCallback(object: UserAdapter.OnItemClickedCallback {
            override fun onItemClicked(data: UserItem) {
                showUserDetail(data)
            }
        })
    }
    private fun showUserDetail(data: UserItem) {
        val toDetailUser = SearchUserFragmentDirections
            .actionSearchUserToDetailuser(
                /* userUrl = */ data.login
            )
        findNavController().navigate(toDetailUser)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}