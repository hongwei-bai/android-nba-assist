package com.hongwei.android_nba_assist.compat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.constant.AppConfigurations.TeamScheduleConfiguration.DAYS_PER_CALENDAR_ROW
import com.hongwei.android_nba_assist.databinding.FragmentCalendarBinding
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.viewmodel.TeamCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentCalendarBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TeamCalendarViewModel by viewModels()

    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

    @Inject
    lateinit var localSettings: LocalSettings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val calendarListAdapter = CalendarListAdapter(applicationContext, localSettings)
        binding.recyclerView.run {
            layoutManager = GridLayoutManager(requireContext(), DAYS_PER_CALENDAR_ROW)
            adapter = calendarListAdapter
        }

        viewModel.matchEvents.observe(this, {
            calendarListAdapter.data = it
        })

        setupSwipeRefreshLayout()
        viewModel.loadCache()
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.warriors_gold)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.load()
        }
    }
}