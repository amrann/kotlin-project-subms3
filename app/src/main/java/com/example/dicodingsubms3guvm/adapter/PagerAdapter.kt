package com.example.dicodingsubms3guvm.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dicodingsubms3guvm.R
import com.example.dicodingsubms3guvm.fragment.FollowersFragment
import com.example.dicodingsubms3guvm.fragment.FollowingFragment

class PagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var usernama: String? = null

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = usernama?.let { FollowingFragment.newInstance(it) }
            1 -> fragment = usernama?.let { FollowersFragment.newInstance(it) }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }
}