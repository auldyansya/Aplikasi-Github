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
class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingListViewHolder>() {

    private var listFollowingUser = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FollowingListViewHolder {
        return FollowingListViewHolder(
                ItemListUserBinding.inflate(
                        LayoutInflater.from(viewGroup.context),
                        viewGroup,
                        false)
        )
    }

    override fun onBindViewHolder(holder: FollowingAdapter.FollowingListViewHolder, position: Int) {
        holder.bind(listFollowingUser[position])
    }

    override fun getItemCount(): Int = listFollowingUser.size

    inner class FollowingListViewHolder(private val binding: ItemListUserBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(following: User) {
            with(binding){
                Glide.with(itemView.context)
                        .load(following.avatar)
                        .override(50, 50)
                        .into(avatarList)
                usernameList.text = following.username
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(following) }
            }
        }
    }

    fun setData(items: ArrayList<User>){
        listFollowingUser.clear()
        listFollowingUser.addAll(items)
        notifyDataSetChanged()
        notifyDataSetChanged()
    }
}