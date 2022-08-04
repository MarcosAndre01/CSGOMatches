package com.example.csgomatches.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.csgomatches.R
import com.example.csgomatches.databinding.RightTeamPlayerItemBinding
import com.example.csgomatches.model.Player

class RightTeamAdapter : RecyclerView.Adapter<RightTeamAdapter.RightPlayerViewHolder>() {
    class RightPlayerViewHolder(val binding: RightTeamPlayerItemBinding) :
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
    ): RightPlayerViewHolder {
        return RightPlayerViewHolder(
            RightTeamPlayerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RightPlayerViewHolder, position: Int) {
        val player = players[position]
        val placeholderImage = ContextCompat.getDrawable(holder.itemView.context,
            R.drawable.player_image_placeholder
        )

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