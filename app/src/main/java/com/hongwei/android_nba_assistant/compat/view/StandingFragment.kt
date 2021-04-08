package com.hongwei.android_nba_assistant.compat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hongwei.android_nba_assistant.databinding.FragmentStandingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StandingFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentStandingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStandingBinding.inflate(inflater, container, false)
        return binding.root
    }
}