package com.auldy.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.auldy.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)


        binding.apply {
            listMain.setHasFixedSize(true)
            listMain.layoutManager = LinearLayoutManager(this@MainActivity)
            listMain.adapter = adapter
        }

        viewModel.setFavoriteUser(this)
        viewModel.getFavoriteUser()?.observe(this,{
            if(it!=null){
                adapter.setData(it)
            }
        })
    }
}