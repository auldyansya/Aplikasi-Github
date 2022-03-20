package com.auldy.github3

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.auldy.github3.adapter.UserAdapter
import com.auldy.github3.databinding.ActivityMainBinding
import com.auldy.github3.model.User
import com.auldy.github3.viewModel.MainViewModel

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel
    private  var adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listMain.setHasFixedSize(true)

        adapter.notifyDataSetChanged()
        showRecyclerView()

    }

    private fun showRecyclerView(){
        adapter = UserAdapter()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.getUsers().observe(this, { user ->
            if (user != null){
                adapter.setData(user)
            }
        })

        binding.listMain.layoutManager = LinearLayoutManager(this)
        binding.listMain.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, data)
                startActivity(intent)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                getDataUserFromApi(query)
                progressBar(false)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                progressBar(true)
                return false
            }
        })
        return true
    }

    fun getDataUserFromApi(users : String){
        if (users.isEmpty())
            return
        mainViewModel.setUser(users)
        progressBar(true)

    }

    private fun progressBar(state: Boolean){
        if (state){
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }


    private fun setMode(selectedMode : Int){
        when (selectedMode){
            R.id.favorite -> {
                val moveFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(moveFavorite)

                }
            R.id.setting -> {
                val moveSetting = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(moveSetting)
                }
            }
        }
}