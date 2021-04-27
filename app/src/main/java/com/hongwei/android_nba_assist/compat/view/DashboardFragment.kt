package com.hongwei.android_nba_assist.compat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.databinding.FragmentDashboardBinding
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.viewmodel.DashboardViewModel
import com.hongwei.android_nba_assist.viewmodel.viewobject.CountdownCaption
import com.hongwei.android_nba_assist.viewmodel.viewobject.CountdownStatus
import com.hongwei.android_nba_assist.viewmodel.viewobject.CountdownUnit
import com.hongwei.android_nba_assist.viewmodel.viewobject.LoadingStatus
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    @Inject
    lateinit var localSettings: LocalSettings

    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

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
        observeTeamTheme()
        setupSwipeRefreshLayout()
        viewModel.loadCache()
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
                Picasso.get()
                    .load(it.homeTeamLogoUrl)
                    .placeholder(it.homeTeamLogoPlaceholder)
                    .into(binding.homeTeamLogo)
                Picasso.get()
                    .load(it.guestTeamLogoUrl)
                    .placeholder(it.guestTeamLogoPlaceholder)
                    .into(binding.guestTeamLogo)
            }

            binding.nextGameLayout.setOnClickListener {
//                findNavController().navigate(R.id.action_dashboard_to_calendar_fragment)
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

    private fun observeTeamTheme() {
        viewModel.teamTheme.observe(this, {
            (activity as NbaTeamTheme).setTeamTheme(localSettings.myTeam)
            Picasso.get()
                .load(it.teamBannerUrl)
                .placeholder(R.drawable.banner_placeholder)
                .into(binding.teamBanner)
        })
    }
}