package com.example.csgomatches.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.csgomatches.R
import com.example.csgomatches.databinding.MatchItemBinding
import com.example.csgomatches.ui.model.Match
import com.example.csgomatches.ui.model.MatchStatus

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

            teams.first.imageUrl?.let {
                firstTeamImage.load(it) {
                    transformations(CircleCropTransformation())
                }
            }
            teams.second.imageUrl?.let {
                secondTeamImage.load(it) {
                    transformations(CircleCropTransformation())
                }
            }

            leagueSerie.text =
                context.getString(R.string.league_serie, item.league, item.serie ?: "")

            matchTimeText.text =
                if (item.status == MatchStatus.Running) {
                    context.getString(R.string.match_time_live)
                } else {
                    item.beginAt?.asString(context)
                }

            if (item.status == MatchStatus.Running) {
                matchTime.background =
                    ContextCompat.getDrawable(context, R.drawable.match_time_live)
            } else {
                matchTime.background =
                    ContextCompat.getDrawable(context, R.drawable.match_time)
            }

            matchCard.setOnClickListener {
                Navigation.findNavController(holder.itemView).navigate(
                    MatchesFragmentDirections.actionMatchesFragmentToMatchDetailFragment(item)
                )
            }
        }
    }

}