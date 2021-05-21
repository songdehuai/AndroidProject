package com.base.app.tools

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlin.collections.ArrayList


class ViewPager2Adapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String = "") {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    fun getFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun getTitles(position: Int): String {
        return fragmentTitleList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }


    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}