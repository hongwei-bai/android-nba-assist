package com.hongwei.android_nba_assist.compat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.databinding.FragmentSplashBinding
import com.hongwei.android_nba_assist.usecase.ForceRequestScheduleUseCase
import com.hongwei.android_nba_assist.usecase.ForceRequestStandingUseCase
import com.hongwei.android_nba_assist.usecase.TeamThemeUseCase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class SplashFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var forceRequestScheduleUseCase: ForceRequestScheduleUseCase

    @Inject
    lateinit var forceRequestStandingUseCase: ForceRequestStandingUseCase

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
            val jobs = listOf(
                async {
                    suspendCoroutine<Any?> { coroutineScope ->
                        Picasso.get()
                            .load(teamThemeUseCase.getTeamTheme().teamBannerUrl)
                            .fetch(object : Callback {
                                override fun onSuccess() {
                                    coroutineScope.resume(null)
                                }

                                override fun onError(e: Exception?) {
                                    coroutineScope.resume(null)
                                }
                            })
                    }
                },
                async {
                    forceRequestScheduleUseCase.forceRequestScheduleFromServer()
                },
                async {
                    forceRequestStandingUseCase.forceRequestStandingFromServer()
                }
            )
            jobs.awaitAll()
            launchMain()
        }
    }

    private fun launchMain() {
        GlobalScope.launch {
            GlobalScope.launch(Dispatchers.Main) {
                findNavController().navigate(R.id.action_launch_dashboard)
            }
        }
    }
}