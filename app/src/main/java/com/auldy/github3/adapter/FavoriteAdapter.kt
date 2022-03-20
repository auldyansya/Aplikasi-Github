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
class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteListViewHolder>() {

    private var listFavorite: ArrayList<User> = ArrayList()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FavoriteListViewHolder {
        return FavoriteListViewHolder(
            ItemListUserBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class FavoriteListViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(Favorite: User) {
            with(binding){
                Glide.with(itemView.context)
                    .load(Favorite.avatar)
                    .override(50, 50)
                    .into(avatarList)
                usernameList.text = Favorite.username
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(Favorite) }
            }
        }
    }

    fun setData(items: ArrayList<User>){
        this.listFavorite = items
        notifyDataSetChanged()
    }
}