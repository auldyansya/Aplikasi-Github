package com.auldy.github3.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.auldy.github3.DetailActivity.Companion.EXTRA_DETAIL
import com.auldy.github3.adapter.FollowersAdapter
import com.auldy.github3.databinding.FragmentFollowersBinding
import com.auldy.github3.model.User
import com.auldy.github3.viewModel.FollowersViewModel


/**
 * Created By Auldy on 15/04/2021.
 *
 */
class FollowersFragment : Fragment() {


    val username = arguments?.getString(ARG_USERNAME)

    companion object {

        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel : FollowersViewModel
    private var adapter = FollowersAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listFollowers.setHasFixedSize(true)
        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()


        binding.listFollowers.layoutManager = LinearLayoutManager(activity)
        binding.listFollowers.adapter = adapter


        val dataUser = activity!!.intent.getParcelableExtra<User>(EXTRA_DETAIL) as User
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        mainViewModel.setFollowers(dataUser.username.toString())
        progressBar(true)
        mainViewModel.getFollowersUsers().observe(viewLifecycleOwner, { followers ->
            if (followers != null){
                adapter.setData(followers)
                progressBar(false)
            }
        })
    }
    private fun progressBar(state: Boolean){
        if (state){
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.INVISIBLE
        }
    }
}