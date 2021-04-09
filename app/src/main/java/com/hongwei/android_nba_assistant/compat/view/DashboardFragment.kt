package com.hongwei.android_nba_assistant.compat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hongwei.android_nba_assistant.R
import com.hongwei.android_nba_assistant.databinding.FragmentDashboardBinding
import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.util.DrawableByNameUtil.getTeamBannerDrawable
import com.hongwei.android_nba_assistant.util.DrawableByNameUtil.getTeamDrawable
import com.hongwei.android_nba_assistant.viewmodel.DashboardViewModel
import com.hongwei.android_nba_assistant.viewmodel.viewobject.CountdownCaption
import com.hongwei.android_nba_assistant.viewmodel.viewobject.CountdownStatus
import com.hongwei.android_nba_assistant.viewmodel.viewobject.CountdownUnit
import com.hongwei.android_nba_assistant.viewmodel.viewobject.LoadingStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    @Inject
    lateinit var localSettings: LocalSettings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        observeLoadingSpinner()
        observeUpcomingGame()
        observeCountDown()
        setupSwipeRefreshLayout()
        binding.teamBanner.setImageDrawable(getTeamBannerDrawable(requireContext(), localSettings.myTeam))
        viewModel.load()
    }

    private fun observeCountDown() {
        viewModel.upcomingGameCountdown.observe(this, {
            with(it) {
                when (countdownUnit) {
                    CountdownUnit.Days -> binding.upcomingGameInDayValue.text =
                        resources.getString(R.string.upcoming_game_in_days, countdownStaticValue)
                    CountdownUnit.Hours -> binding.upcomingGameInDayValue.text =
                        resources.getString(R.string.upcoming_game_in_hours, countdownStaticValue)
                    CountdownUnit.Countdown -> {
                        // No-Op
                    }
                }
                binding.upcomingGameInDayCaption.text = when (countdownCaption) {
                    CountdownCaption.On -> resources.getString(R.string.upcoming_game_on)
                    CountdownCaption.In -> resources.getString(R.string.upcoming_game_in)
                }
            }
        })

        viewModel.countdownDynamicValue.observe(this, {
            binding.upcomingGameInDayValue.text = it
        })

        viewModel.countdownStatus.observe(this, { countdownStatus ->
            when (countdownStatus) {
                CountdownStatus.Now -> binding.upcomingGameInDayValue.text = resources.getString(R.string.upcoming_game_now)
                CountdownStatus.CountdownZero -> binding.upcomingGameInDayValue.text = resources.getString(R.string.upcoming_game_countdown_zero)
                CountdownStatus.Started -> binding.upcomingGameInDayCaption.text = resources.getString(R.string.upcoming_game_started)
                CountdownStatus.Today -> binding.upcomingGameInDayValue.text = resources.getString(R.string.upcoming_game_today)
                CountdownStatus.Tomorrow -> binding.upcomingGameInDayValue.text = resources.getString(R.string.upcoming_game_tomorrow)
                else -> {
                    // No-Op
                }
            }
        })
    }

    private fun observeUpcomingGame() {
        viewModel.gamesLeft.observe(this, { gameLeft ->
            if (gameLeft > 0) {
                binding.gamesLeftNumber.text = "$gameLeft"
                binding.nextGameLayout.visibility = View.VISIBLE
            } else {
                displayNoUpcomingGames()
            }
        })

        viewModel.upcomingGameInfo.observe(this, {
            with(it) {
                binding.gamesLeftCaption.text = resources.getString(R.string.games_left)
                binding.gameDate.text = dateString
                binding.gameTime.text = timeString
                binding.guestTeamLogo.setImageDrawable(getTeamDrawable(requireContext(), guestTeamShort))
                binding.homeTeamLogo.setImageDrawable(getTeamDrawable(requireContext(), homeTeamShort))
            }

            binding.nextGameLayout.setOnClickListener {
                findNavController().navigate(R.id.action_dashboard_to_calendar_fragment)
            }
        })
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.warriors_gold)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.load()
        }
    }

    private fun displayNoUpcomingGames() {
        binding.gamesLeftNumber.text = ""
        binding.gamesLeftCaption.text = resources.getString(R.string.no_games_left)
        binding.nextGameLayout.visibility = View.INVISIBLE
    }

    private fun observeLoadingSpinner() {
        viewModel.loadingStatus.observe(this, { loadingStatus ->
            binding.loadingSpinner.visibility = if (loadingStatus == LoadingStatus.Loading) View.VISIBLE else View.GONE

            val contentVisibility = if (loadingStatus == LoadingStatus.Inactive) View.VISIBLE else View.GONE
            binding.gamesLeftNumber.visibility = contentVisibility
            binding.gamesLeftCaption.visibility = contentVisibility
            binding.upcomingGameInDayCaption.visibility = contentVisibility
            binding.upcomingGameInDayValue.visibility = contentVisibility
            binding.nextGameLayout.visibility = contentVisibility
            binding.nextGameShadow.visibility = contentVisibility

            binding.errorText.visibility = if (loadingStatus == LoadingStatus.Error) View.VISIBLE else View.GONE
        })
    }
}