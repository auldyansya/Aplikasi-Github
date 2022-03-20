package com.auldy.github3.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.auldy.github3.DetailActivity.Companion.EXTRA_DETAIL
import com.auldy.github3.adapter.FollowingAdapter
import com.auldy.github3.databinding.FragmentFollowingBinding
import com.auldy.github3.model.User
import com.auldy.github3.viewModel.FollowingViewModel

/**
 * Created By Auldy on 15/04/2021.
 *
 */
class FollowingFragment : Fragment() {

    val username = arguments?.getString(ARG_USERNAME)

    companion object {

        private const val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel : FollowingViewModel
    private var adapter = FollowingAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listFollowing.setHasFixedSize(true)
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()


        binding.listFollowing.layoutManager = LinearLayoutManager(activity)
        binding.listFollowing.adapter = adapter


        val dataUser = activity!!.intent.getParcelableExtra<User>(EXTRA_DETAIL) as User
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        mainViewModel.setFollowing(dataUser.username.toString())
        progressBar(true)
        mainViewModel.getFollowingUsers().observe(viewLifecycleOwner, { following ->
            if (following != null){
                adapter.setData(following)
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