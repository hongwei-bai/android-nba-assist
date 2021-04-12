package com.hongwei.android_nba_assistant.compat.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hongwei.android_nba_assistant.R
import com.hongwei.android_nba_assistant.constant.AppConfigurations
import com.hongwei.android_nba_assistant.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed(
            {
                findNavController().navigate(R.id.action_splash_to_dashboard_fragment)
            }, AppConfigurations.Splash.SPLASH_TIME_OUT
        )
    }
}