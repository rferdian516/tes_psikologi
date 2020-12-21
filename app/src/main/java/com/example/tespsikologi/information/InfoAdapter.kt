package com.example.tespsikologi.information

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tespsikologi.R

class InfoAdapter (manager: FragmentManager,
                   private val context : Context
) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // Returns total number of pages
    override fun getCount(): Int {
        return NUM_ITEMS
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> InfoFragment.newInstance(
                context.resources.getString(R.string.audiotori),
                context.resources.getString(R.string.des_A),
                R.raw.audiotori
            )
            1 -> InfoFragment.newInstance(
                context.resources.getString(R.string.visual),
                context.resources.getString(R.string.des_V),
                R.raw.visual
            )
            2 -> InfoFragment.newInstance(
                context.resources.getString(R.string.kinestetik),
                context.resources.getString(R.string.des_K),
                R.raw.kinestetik
            )
            else -> null
        }!!
    }

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }

    companion object {
        private const val NUM_ITEMS = 3
    }

}