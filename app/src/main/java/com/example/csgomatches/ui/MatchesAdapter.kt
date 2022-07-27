package com.example.csgomatches.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.csgomatches.R
import com.example.csgomatches.databinding.MatchItemBinding
import com.example.csgomatches.ui.model.Match
import com.example.csgomatches.ui.model.MatchStatus

private const val TAG = "MatchAdapter"

class MatchesAdapter : PagingDataAdapter<Match, MatchesAdapter.MatchViewHolder>(DiffCallback) {
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

            val placeholderImage = ContextCompat.getDrawable(context, R.drawable.ic_image_placeholder)
            firstTeamImage.setImageDrawable(placeholderImage)
            teams.first.imageUrl?.let { firstTeamImage.load(it) { placeholder(placeholderImage) } }
            secondTeamImage.setImageDrawable(placeholderImage)
            teams.second.imageUrl?.let { secondTeamImage.load(it) { placeholder(placeholderImage) } }

            leagueSerie.text =
                context.getString(R.string.league_serie, item.league, item.serie ?: "")

            matchTimeText.text = getMatchTimeText(item, context)
            if (item.status == MatchStatus.Running) {
                matchTime.background =
                    ContextCompat.getDrawable(context, R.drawable.match_time_live)
            } else {
                matchTime.background =
                    ContextCompat.getDrawable(context, R.drawable.match_time)
            }
            
            matchCard.setOnClickListener {
               Navigation.findNavController(holder.itemView).navigate(MatchesFragmentDirections.actionMatchesFragmentToMatchDetailFragment(item))
            }
        }
    }

    private fun getMatchTimeText(match: Match, context: Context): String? {
        if (match.status == MatchStatus.Running) {
            return context.getString(R.string.match_time_live)
        }

        return formatDate(match.beginAt, context)
    }
}