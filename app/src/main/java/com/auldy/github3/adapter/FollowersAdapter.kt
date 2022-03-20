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
class FollowersAdapter: RecyclerView.Adapter<FollowersAdapter.FollowersListViewHolder>() {

    private var listFollowersUser = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FollowersListViewHolder {
        return FollowersListViewHolder(
                ItemListUserBinding.inflate(
                        LayoutInflater.from(viewGroup.context),
                        viewGroup,
                        false)
        )
    }
    override fun onBindViewHolder(holder: FollowersAdapter.FollowersListViewHolder, position: Int) {
        holder.bind(listFollowersUser[position])
    }

    override fun getItemCount(): Int = listFollowersUser.size


    inner class FollowersListViewHolder(private val binding: ItemListUserBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(Followers: User) {
            with(binding){
                Glide.with(itemView.context)
                        .load(Followers.avatar)
                        .override(50, 50)
                        .into(avatarList)
                usernameList.text = Followers.username
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(Followers) }
            }
        }
    }

    fun setData(items: ArrayList<User>){
        listFollowersUser.clear()
        listFollowersUser.addAll(items)
        notifyDataSetChanged()
    }
}