package com.example.githubuserapp.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.UserItem
import com.example.githubuserapp.data.response.UsersResponse
import com.example.githubuserapp.data.retrofit.ApiConfig
import com.example.githubuserapp.databinding.FragmentSearchUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

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
                .setOnEditorActionListener { textView, actionId, event ->
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}