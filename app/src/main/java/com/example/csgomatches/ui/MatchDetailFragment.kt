package com.example.csgomatches.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.csgomatches.R
import com.example.csgomatches.data.tournaments.TournamentsRepository
import com.example.csgomatches.data.tournaments.service.TournamentsRemoteDataSource
import com.example.csgomatches.databinding.FragmentMatchDetailBinding
import com.example.csgomatches.ui.model.Match

private const val TAG = "MatchDetailFragment"

class MatchDetailFragment : Fragment() {
    private val viewModel: MatchDetailViewModel by viewModels {
        MatchDetailViewModel.Factory(
            TournamentsRepository(TournamentsRemoteDataSource)
        )
    }
    private lateinit var binding: FragmentMatchDetailBinding
    private val args by navArgs<MatchDetailFragmentArgs>()

    private val leftTeamAdapter = LeftTeamAdapter()
    private val rightTeamAdapter = RightTeamAdapter()

    init {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            bindMatchInfo(uiState.match)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedMatch(args.selectedMatch)

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        bindRecyclerViews()
    }

    private fun bindRecyclerViews() {
        binding.leftTeamList.adapter = leftTeamAdapter
        binding.leftTeamList.layoutManager = LinearLayoutManager(context)

        binding.rightTeamList.adapter = rightTeamAdapter
        binding.rightTeamList.layoutManager = LinearLayoutManager(context)
    }

    private fun bindMatchInfo(match: Match) {
        binding.leagueSerieTitle.text =
            getString(R.string.league_serie, match.league, match.serie ?: "")
        binding.date.text = formatDate(match.beginAt)

        binding.firstTeamName.text = match.teams.first.name
        binding.secondTeamName.text = match.teams.second.name

        match.teams.first.imageUrl?.let { binding.firstTeamImage.load(it) { placeholder(R.drawable.ic_image_placeholder) } }
        match.teams.second.imageUrl?.let { binding.secondTeamImage.load(it) { placeholder(R.drawable.ic_image_placeholder) } }

        updateAdapters(match)
    }

    private fun updateAdapters(match: Match) {
        val leftTeamPlayers = match.teams.first.players
        if (leftTeamPlayers != null) {
            leftTeamAdapter.players = leftTeamPlayers
        }

        val rightTeamPlayers = match.teams.second.players
        if (rightTeamPlayers != null) {
            rightTeamAdapter.players = rightTeamPlayers
        }
    }

}