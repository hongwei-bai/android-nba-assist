package com.hongwei.android_nba_assistant.compat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hongwei.android_nba_assistant.databinding.FragmentCalendarBinding
import com.hongwei.android_nba_assistant.viewmodel.WarriorsCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentCalendarBinding? = null

    private val binding get() = _binding!!

    private val viewModel: WarriorsCalendarViewModel by viewModels()

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

        viewModel.matchEvents.observe(this, {
            val gamesLeft = it.size
            val incomingGameTime = it.first().date
            val localTime =
                SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US).format(incomingGameTime.time)
        })
    }
}