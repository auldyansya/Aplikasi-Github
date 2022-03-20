package com.auldy.github3

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.auldy.github3.adapter.PagerAdapter
import com.auldy.github3.databinding.ActivityDetailBinding
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.NAME
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.REPOSITORY
import com.auldy.github3.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.auldy.github3.helper.MappingHelper
import com.auldy.github3.db.FavoriteHelper
import com.auldy.github3.model.User
import com.auldy.github3.viewModel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel : DetailViewModel
    private var isFav = false
    private var user: User? = null
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        user = intent.getParcelableExtra(EXTRA_DETAIL)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = user?.username

        val detail = intent.getParcelableExtra<User>(EXTRA_DETAIL) as User

        val sectionsPagerAdapter = PagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = detail.username
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        supportActionBar?.elevation = 0f

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detail.username?.let { detailViewModel.setDetailUser(it) }
        detailViewModel.getDetailUsers().observe(this, {
            binding.apply {
                Glide.with(this@DetailActivity)
                        .load(it.avatar)
                        .apply(RequestOptions()).override(100,100)
                        .into(avatarList)
                usernameList.text = it.username
                nameList.text = it.name
                tvLocation.text = it.location
                tvCompany.text = it.company
                tvRepository.text = it.repository
                tvFollowers.text = it.followers
                tvFollowing.text = it.following
            }
        })

        checkFavUser()

        binding.btnFavorite.setOnClickListener {
            val values = ContentValues()
            values.put(AVATAR, user?.avatar)
            values.put(USERNAME, user?.username)
            values.put(NAME, user?.name)
            values.put(LOCATION, user?.location)
            values.put(COMPANY, user?.company)
            values.put(REPOSITORY, user?.repository)
            values.put(FOLLOWERS, user?.followers)
            values.put(FOLLOWING, user?.following)

            when {
                isFav -> {
                    favoriteHelper.deleteByUsername(user?.username.toString())
                    Toast.makeText(this@DetailActivity, "${user?.username.toString()} removed from favourite", Toast.LENGTH_SHORT).show()
                    isButtonFavoriteChecked(false)
                    isFav = false
                }

                !isFav -> {
                    favoriteHelper.insert(values)
                    Toast.makeText(this@DetailActivity, "${user?.username.toString()} added to favourite", Toast.LENGTH_SHORT).show()
                    isButtonFavoriteChecked(true)
                    isFav = true
                }
            }
        }
    }


    private fun isButtonFavoriteChecked(state: Boolean){
        if (state){
            binding.btnFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.btnFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun checkFavUser(){
        val username = user?.username
        val cursor = favoriteHelper.queryByUsername(username.toString())
        val mFav = MappingHelper.mapCursorToArrayList(cursor)

        for (mUser in mFav){
            if (username == mUser.username){
                isButtonFavoriteChecked(true)
                isFav = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }


}