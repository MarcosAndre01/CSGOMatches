package com.example.csgomatches.ui.match_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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
import com.example.csgomatches.ui.formatDate
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

        bindHeader(args.selectedMatch)
        bindRecyclerViews()

        observeUiState()
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            if (uiState.isLoading) {
                showLoadingIndicator()
            } else {
                bindMatchInfo(uiState.match)
                uiState.errorMessage?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
                hideLoadingIndicator()
            }
        }
    }

    private fun bindHeader(match: Match) {
        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.leagueSerieTitle.text =
            getString(R.string.league_serie, match.league, match.serie ?: "")
    }

    private fun bindRecyclerViews() {
        binding.leftTeamList.adapter = leftTeamAdapter
        binding.leftTeamList.layoutManager = LinearLayoutManager(context)

        binding.rightTeamList.adapter = rightTeamAdapter
        binding.rightTeamList.layoutManager = LinearLayoutManager(context)
    }

    private fun bindMatchInfo(match: Match) {
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

    private fun showLoadingIndicator() {
        binding.loading.isVisible = true
        binding.matchInfo.isVisible = false
    }

    private fun hideLoadingIndicator() {
        binding.matchInfo.isVisible = true
        binding.loading.isVisible = false
    }

}