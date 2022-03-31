package com.example.csgomatches.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

        val adapter = MatchesAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.matches.collectLatest { matches ->
                    adapter.submitData(matches)
                }
            }
        }
    }
}