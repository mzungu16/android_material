package com.gleb.android_material

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import geekbarains.material.ui.api.EarthFragment
import geekbarains.material.ui.api.MarsFragment

private const val EARTH = 0
private const val MARS = 1
private const val SYSTEM = 2

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val listOfFragments = listOf(EarthFragment(), MarsFragment(), SystemFragment())

    override fun getItemCount(): Int {
        return listOfFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> listOfFragments[EARTH]
            1 -> listOfFragments[MARS]
            2 -> listOfFragments[SYSTEM]
            else -> listOfFragments[0]
        }
    }
}