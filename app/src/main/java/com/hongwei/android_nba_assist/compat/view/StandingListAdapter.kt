package com.hongwei.android_nba_assist.compat.view

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.compat.view.StandingListAdapter.Companion.StandingViewType.DATA
import com.hongwei.android_nba_assist.compat.view.StandingListAdapter.Companion.StandingViewType.HEADER
import com.hongwei.android_nba_assist.databinding.LayoutStandingHeaderBinding
import com.hongwei.android_nba_assist.databinding.LayoutStandingItemBinding
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.viewmodel.viewobject.TeamStandingViewObject
import com.squareup.picasso.Picasso
import java.util.*


class StandingListAdapter(
    private val applicationContext: Context,
    private val localSettings: LocalSettings
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: List<TeamStandingViewObject> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            HEADER -> HeaderViewHolder(
                LayoutStandingHeaderBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> DataViewHolder(
                LayoutStandingItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > 0) {
            val isMyTeam = data[position - 1].teamAbbr.toLowerCase(Locale.ROOT) == localSettings.myTeam
            (holder as DataViewHolder).bind(applicationContext, data[position - 1], isMyTeam)
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) HEADER else DATA

    override fun getItemCount(): Int = data.size + 1

    class HeaderViewHolder(binding: LayoutStandingHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    class DataViewHolder(private val binding: LayoutStandingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, itemData: TeamStandingViewObject, isMyTeam: Boolean = false) {
            binding.rank.text = itemData.rank.toString()
            binding.team.text = itemData.teamAbbr.toUpperCase(Locale.ROOT)
            Picasso.get().load(itemData.logoUrl).placeholder(itemData.logoPlaceholder).into(binding.logo)
            binding.wins.text = itemData.wins.toString()
            binding.losses.text = itemData.losses.toString()
            binding.pct.text = itemData.pct.toString()
            binding.gamesBack.text = itemData.gamesBack.toString()
            binding.ppgDiff.text = itemData.avePointsDiff.toString()
            binding.streak.text = "${itemData.currentStreak.first}${itemData.currentStreak.second}"
            binding.l10.text = "${itemData.last10Records.first}-${itemData.last10Records.second}"

            val textViews = binding.run {
                listOf(rank, team, wins, losses, pct, gamesBack, ppgDiff, streak, l10)
            }
            textViews.forEach {
                it.setTextColor(
                    ColorStateList.valueOf(
                        context.getColor(
                            if (isMyTeam) {
                                R.color.warriors_blue
                            } else {
                                when (itemData.rank) {
                                    in 0..6 -> R.color.warriors_gold
                                    in 7..8 -> R.color.white
                                    in 9..10 -> R.color.grey40
                                    else -> R.color.grey60
                                }
                            }
                        )
                    )
                )
            }
            binding.root.background = ContextCompat.getDrawable(
                context,
                if (isMyTeam) R.color.warriors_gold else R.color.calendar_black_alpha_background
            )
        }
    }

    companion object {
        object StandingViewType {
            const val HEADER = 0
            const val DATA = 1
        }
    }
}