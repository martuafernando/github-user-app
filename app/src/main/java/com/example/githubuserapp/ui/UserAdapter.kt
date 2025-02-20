package com.example.githubuserapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.UserItem
import com.example.githubuserapp.databinding.ItemUserBinding

class UserAdapter: ListAdapter<UserItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickedCallback: OnItemClickedCallback
    interface OnItemClickedCallback {
        fun onItemClicked(data: UserItem)
    }
    fun setOnItemClickedCallback(onItemClickedCallback: OnItemClickedCallback) {
        this.onItemClickedCallback = onItemClickedCallback
    }
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

        holder.itemView.setOnClickListener { onItemClickedCallback.onItemClicked(user) }
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem){
            binding.tvItemUserName.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.imgUserPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}