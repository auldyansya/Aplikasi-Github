package com.auldy.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auldy.consumerapp.databinding.ItemListUserBinding
import com.bumptech.glide.Glide

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteListViewHolder>() {

    private var listFavorite: ArrayList<Favorite> = ArrayList()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
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
        fun bind(favorite: Favorite) {
            with(binding){
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .override(50, 50)
                    .into(avatarList)
                usernameList.text = favorite.username
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(favorite) }
            }
        }
    }

    fun setData(items: ArrayList<Favorite>){
        this.listFavorite = items
        notifyDataSetChanged()
    }
}