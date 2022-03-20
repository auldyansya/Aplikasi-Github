package com.auldy.github3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.auldy.github3.adapter.FavoriteAdapter
import com.auldy.github3.databinding.ActivityFavoriteBinding
import com.auldy.github3.helper.MappingHelper
import com.auldy.github3.db.FavoriteHelper
import com.auldy.github3.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userAdapter: FavoriteAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorites"

        configRecyclerView()

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        if (savedInstanceState == null){
            loadAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null){
                userAdapter.setData(list)
            }
        }

    }

    private fun loadAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            progressBar(true)
            val deferredNotes = async(Dispatchers.IO){
                val cursor = favoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressBar(false)
            val users = deferredNotes.await()
            if (users.size > 0){
                userAdapter.setData(users)
            } else {
                Snackbar.make(binding.listFavorite, "Tidak ada data saat ini", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun configRecyclerView(){
        binding.listFavorite.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            userAdapter = FavoriteAdapter()
            adapter = userAdapter
        }

        userAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                clickedItem(data)
            }

        })
    }

    private fun clickedItem(users: User){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL, users)
        startActivity(intent)
    }

    private fun progressBar(state:  Boolean){
        if (state){
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchView = menu.findItem(R.id.search)
        searchView.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return true
    }

    override fun onResume() {
        super.onResume()
        loadAsync()
    }
}