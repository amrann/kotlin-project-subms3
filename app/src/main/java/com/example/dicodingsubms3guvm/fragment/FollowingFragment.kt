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
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    private lateinit var adapter : ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
        private val ARG_USERNAME = "username"

        fun newInstance(username: String) : FollowingFragment {
            val fragment = FollowingFragment()
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
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ListUserAdapter()
        idListFollowingLayout.adapter = adapter
        idListFollowingLayout.layoutManager = LinearLayoutManager(activity)

        mainViewModel = ViewModelProvider (
            this,
            ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java
        )

        val username = arguments?.getString(ARG_USERNAME)

        if (username != null) {
            showLoading(true)
            mainViewModel.setAPIFollowingUser(username)
        }

        mainViewModel.getAPIFollowingUser().observe(viewLifecycleOwner, Observer { followingItems ->
            if (followingItems != null) {
                adapter.setData(followingItems)
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