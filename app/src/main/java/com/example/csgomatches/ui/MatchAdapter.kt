package com.example.csgomatches.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.csgomatches.R
import com.example.csgomatches.databinding.MatchItemBinding
import com.example.csgomatches.ui.model.Match
import com.example.csgomatches.ui.model.MatchStatus
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "MatchAdapter"

class MatchAdapter : PagingDataAdapter<Match, MatchAdapter.MatchViewHolder>(DiffCallback) {
    class MatchViewHolder(val binding: MatchItemBinding) : RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            MatchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val item = getItem(position) ?: return
        val context = holder.itemView.context
        val teams = item.teams

        holder.binding.apply {
            firstTeamName.text = teams.first.name
            secondTeamName.text = teams.second.name
            teams.first.imageUrl?.let { firstTeamImage.load(it) { placeholder(R.drawable.ic_image_placeholder) } }
            teams.second.imageUrl?.let { secondTeamImage.load(it) { placeholder(R.drawable.ic_image_placeholder) } }

            leagueSerieTitle.text =
                context.getString(R.string.league_serie, item.league, item.serie)

            matchTimeText.text = getMatchTimeText(item, context)
            if (item.status == MatchStatus.Running) {
                matchTime.background =
                    ContextCompat.getDrawable(context, R.drawable.match_time_live)
            } else {
                matchTime.background =
                    ContextCompat.getDrawable(context, R.drawable.match_time)
            }
        }
    }

    private fun getMatchTimeText(match: Match, context: Context): String? {
        val beginAt = match.beginAt ?: return null

        if (match.status == MatchStatus.Running) {
            return context.getString(R.string.match_time_live)
        }

        val closeDate = SimpleDateFormat("EEE HH:mm", Locale.getDefault())
        val farDate = SimpleDateFormat("dd.MM HH:mm", Locale.getDefault())

        val diff = beginAt.time - Date().time
        val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        if (days in 0..6) {
            Log.d(TAG, "getMatchTimeText: $beginAt")
            return closeDate.format(beginAt)
        }
        return farDate.format(beginAt)
    }
}