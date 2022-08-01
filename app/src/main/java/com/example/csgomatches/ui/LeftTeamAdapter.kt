package com.example.csgomatches.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.csgomatches.R
import com.example.csgomatches.databinding.LeftTeamPlayerItemBinding
import com.example.csgomatches.ui.model.Player

class LeftTeamAdapter : RecyclerView.Adapter<LeftTeamAdapter.LeftPlayerViewHolder>() {
    class LeftPlayerViewHolder(val binding: LeftTeamPlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.nickname == newItem.nickname
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var players: List<Player>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeftPlayerViewHolder {
        return LeftPlayerViewHolder(
            LeftTeamPlayerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LeftPlayerViewHolder, position: Int) {
        val player = players[position]
        val placeholderImage = ContextCompat.getDrawable(holder.itemView.context, R.drawable.player_image_placeholder)

        holder.binding.apply {
            playerNickname.text = player.nickname
            playerFullName.text = player.name
            playerImage.setImageDrawable(placeholderImage)
            if (player.imageUrl != null) {
                playerImage.load(player.imageUrl) {
                    placeholder(placeholderImage)
                    transformations(RoundedCornersTransformation(8f))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }


}