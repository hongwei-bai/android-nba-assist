package com.hongwei.android_nba_assist.compat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.databinding.FragmentStandingBinding
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.compat.viewmodel.StandingViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class StandingFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentStandingBinding? = null

    private val binding get() = _binding!!

    private val viewModel: StandingViewModel by viewModels()

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
        _binding = FragmentStandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val standingListAdapter = StandingListAdapter(applicationContext, localSettings)
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = standingListAdapter
        }

        viewModel.standingViewObjects.observe(this, {
            standingListAdapter.data = it
            standingListAdapter.notifyDataSetChanged()
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