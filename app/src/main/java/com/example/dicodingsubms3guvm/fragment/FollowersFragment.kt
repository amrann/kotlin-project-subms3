package com.example.dicodingsubms3guvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubms3guvm.MainViewModel
import com.example.dicodingsubms3guvm.R
import com.example.dicodingsubms3guvm.adapter.ListUserAdapter
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    private lateinit var adapter : ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String) : FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ListUserAdapter()
        idListFollowerLayout.adapter = adapter
        idListFollowerLayout.layoutManager = LinearLayoutManager(activity)

        mainViewModel = ViewModelProvider (
            this,
            ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java
        )

        val username = arguments?.getString(FollowersFragment.ARG_USERNAME)

        if (username != null) {
            showLoading(true)
            mainViewModel.setAPIFollowersUser(username)
        }

        mainViewModel.getAPIFollowersUser().observe(viewLifecycleOwner, Observer { followersItems ->
            if (followersItems != null) {
                adapter.setData(followersItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}