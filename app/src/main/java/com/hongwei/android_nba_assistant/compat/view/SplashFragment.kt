package com.hongwei.android_nba_assistant.compat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hongwei.android_nba_assistant.R
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Splash.SPLASH_DELAY
import com.hongwei.android_nba_assistant.databinding.FragmentSplashBinding
import com.hongwei.android_nba_assistant.usecase.TeamThemeUseCase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    @Inject
    lateinit var teamThemeUseCase: TeamThemeUseCase

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            val ts0 = System.currentTimeMillis()
            Picasso.get()
                .load(teamThemeUseCase.getTeamTheme().teamBannerUrl)
                .fetch(object : Callback {
                    override fun onSuccess() {
                        launchMain(ts0)
                    }

                    override fun onError(e: Exception?) {
                        launchMain(ts0)
                    }
                })
        }
    }

    private fun launchMain(ts0: Long) {
        GlobalScope.launch {
            val consumed = System.currentTimeMillis() - ts0
            delay(0L.coerceAtLeast(SPLASH_DELAY - consumed))
            GlobalScope.launch(Dispatchers.Main) {
                findNavController().navigate(R.id.action_launch_dashboard)
            }
        }
    }
}