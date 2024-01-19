package com.example.githubuserapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.RelatedUser
import com.example.githubuserapp.databinding.ItemUserBinding

class RelatedUserAdapter: ListAdapter<RelatedUser, RelatedUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: RelatedUser){
            binding.tvItemUserName.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.imgUserPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RelatedUser>() {
            override fun areItemsTheSame(oldItem: RelatedUser, newItem: RelatedUser): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: RelatedUser, newItem: RelatedUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}