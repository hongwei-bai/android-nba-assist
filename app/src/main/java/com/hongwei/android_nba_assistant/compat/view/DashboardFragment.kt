package com.hongwei.android_nba_assistant.compat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.hongwei.android_nba_assistant.R
import com.hongwei.android_nba_assistant.databinding.FragmentDashboardBinding
import com.hongwei.android_nba_assistant.viewmodel.WarriorsCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    private val viewModel: WarriorsCalendarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.matchEvents.observe(this, {
            val gamesLeft = it.size
            val incomingGameTime = it.first().date
            val localTime =
                SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US).format(incomingGameTime.time)

            val stringBuilder = StringBuilder().apply {
                append("$gamesLeft games left for Warriors\n")
                append("Incoming game: $localTime.")
            }

            binding.content.text = stringBuilder.toString()
        })

        binding.content.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_dashboard_to_calendar_fragment)
        }
    }
}