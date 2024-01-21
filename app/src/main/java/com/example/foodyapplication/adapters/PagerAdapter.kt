package com.example.foodyapplication.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foody.models.Result

class PagerAdapter(
    fragmentActivity: FragmentActivity,
    private val resultBundle: Bundle,
    private val titles: List<String>,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {



    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
         fragments[position].arguments = resultBundle
        return fragments[position]
    }

     fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }




}
