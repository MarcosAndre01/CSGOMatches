package com.example.csgomatches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csgomatches.databinding.FragmentMatchesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "MatchesFragment"

@AndroidEntryPoint
class MatchesFragment : Fragment() {
    private val viewModel by viewModels<MatchesViewModel>()

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
        binding.refresh.setOnRefreshListener {
            adapter.refresh()
        }

        observeMatches()
        observeLoadState()
    }

    private fun observeMatches() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.matches.collectLatest { matches ->
                adapter.submitData(matches)
            }
        }
    }

    private fun observeLoadState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.loading.isVisible =
                    loadStates.refresh is LoadState.Loading && adapter.itemCount < 1

                binding.refresh.isRefreshing =
                    loadStates.refresh is LoadState.Loading && adapter.itemCount >= 1

                if (loadStates.refresh is LoadState.Error) {
                    val error = (loadStates.refresh as LoadState.Error).error
                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}