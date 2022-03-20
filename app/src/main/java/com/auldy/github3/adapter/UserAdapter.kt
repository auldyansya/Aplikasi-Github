package com.auldy.github3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auldy.github3.databinding.ItemListUserBinding
import com.auldy.github3.model.User
import com.bumptech.glide.Glide

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class UserAdapter : RecyclerView.Adapter<UserAdapter.UserListViewHolder>() {

    private var listUser: ArrayList<User> = ArrayList()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserListViewHolder {
        return UserListViewHolder(
                ItemListUserBinding.inflate(
                        LayoutInflater.from(viewGroup.context),
                        viewGroup,
                        false)
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserListViewHolder(private val binding: ItemListUserBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding){
                Glide.with(itemView.context)
                        .load(user.avatar)
                        .override(50, 50)
                        .into(avatarList)
                usernameList.text = user.username
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    fun setData(items: ArrayList<User>){
        this.listUser = items
        notifyDataSetChanged()
    }
}