package com.example.csgomatches.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.csgomatches.data.tournaments.TournamentsRepository
import com.example.csgomatches.data.tournaments.service.TournamentsRemoteDataSource
import com.example.csgomatches.databinding.FragmentMatchDetailBinding

class MatchDetailFragment : Fragment() {
    private val viewModel: MatchDetailViewModel by viewModels {
        MatchDetailViewModel.Factory(
            TournamentsRepository(TournamentsRemoteDataSource)
        )
    }
    private lateinit var binding: FragmentMatchDetailBinding
    private val args by navArgs<MatchDetailFragmentArgs>()

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
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.text.text = uiState.match.toString()
        }
    }

}