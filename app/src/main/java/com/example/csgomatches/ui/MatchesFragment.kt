package com.example.csgomatches.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csgomatches.data.matches.MatchesRepository
import com.example.csgomatches.data.matches.service.MatchesRemoteDataSource
import com.example.csgomatches.databinding.FragmentMatchesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MatchesFragment : Fragment() {
    private val viewModel: MatchesViewModel by viewModels {
        MatchesViewModel.Factory(
            MatchesRepository(MatchesRemoteDataSource)
        )
    }
    private lateinit var binding: FragmentMatchesBinding

    private val adapter = MatchesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        observeMatches()
        observeLoadState()
    }

    private fun observeMatches() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.matches.collectLatest { matches ->
                    adapter.submitData(matches)
                }
            }
        }
    }

    private fun observeLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest { loadStates ->
                    binding.recyclerView.isVisible = loadStates.refresh !is LoadState.Loading
                    binding.loading.isVisible = loadStates.refresh is LoadState.Loading

                    if (loadStates.refresh is LoadState.Error) {
                        val error = (loadStates.refresh as LoadState.Error).error

                        Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}