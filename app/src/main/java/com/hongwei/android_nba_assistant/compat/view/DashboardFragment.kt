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
import com.hongwei.android_nba_assistant.model.LocalSettings
import com.hongwei.android_nba_assistant.util.DrawableByNameUtil.getTeamBannerDrawable
import com.hongwei.android_nba_assistant.util.DrawableByNameUtil.getTeamDrawable
import com.hongwei.android_nba_assistant.viewmodel.DashboardViewModel
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
        binding.teamBanner.setImageDrawable(getTeamBannerDrawable(requireContext(), localSettings.myTeam))
        viewModel.loadingStatus.observe(this, { loading ->
            binding.loadingSpinner.visibility = if (loading) View.VISIBLE else View.GONE

            val contentVisibility = if (loading) View.GONE else View.VISIBLE
            binding.gamesLeftNumber.visibility = contentVisibility
            binding.gamesLeftCaption.visibility = contentVisibility
            binding.upcomingGameInDayCaption.visibility = contentVisibility
            binding.upcomingGameInDayValue.visibility = contentVisibility
            binding.nextGameLayout.visibility = contentVisibility
        })
        viewModel.upcomingGame.observe(this, {
            it?.run {
                binding.gamesLeftNumber.text = "$gamesLeft"
                binding.gamesLeftCaption.text = resources.getString(R.string.games_left)
                binding.gameDate.text = dateString
                binding.gameTime.text = timeString
                binding.guestTeamLogo.setImageDrawable(getTeamDrawable(requireContext(), guestTeamShort))
                binding.homeTeamLogo.setImageDrawable(getTeamDrawable(requireContext(), homeTeamShort))
                binding.upcomingGameInDayCaption.text = when (inDays) {
                    0, 1 -> resources.getString(R.string.upcoming_game_on)
                    else -> resources.getString(R.string.upcoming_game_in)
                }
                binding.upcomingGameInDayValue.text = when (inDays) {
                    0 -> resources.getString(R.string.upcoming_game_today)
                    1 -> resources.getString(R.string.upcoming_game_tomorrow)
                    else -> resources.getString(R.string.upcoming_game_in_days, inDays)
                }
                binding.nextGameLayout.visibility = View.VISIBLE
            } ?: displayNoUpcomingGames()

            binding.nextGameLayout.setOnClickListener {
                findNavController().navigate(R.id.action_dashboard_to_calendar_fragment)
            }
        })
    }

    private fun displayNoUpcomingGames() {
        binding.gamesLeftNumber.text = ""
        binding.gamesLeftCaption.text = resources.getString(R.string.no_games_left)
        binding.nextGameLayout.visibility = View.INVISIBLE
    }

}