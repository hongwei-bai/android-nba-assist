package com.hongwei.android_nba_assist.compat.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var dashboardFragment: DashboardFragment = DashboardFragment()
    private var calendarFragment: CalendarFragment = CalendarFragment()
    private var standingFragment: StandingFragment = StandingFragment()

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> dashboardFragment
        1 -> calendarFragment
        2 -> standingFragment
        else -> dashboardFragment
    }
}