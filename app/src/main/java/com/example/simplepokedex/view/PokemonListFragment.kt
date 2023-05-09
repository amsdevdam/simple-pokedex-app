package com.example.simplepokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplepokedex.adapter.PokemonAdapter
import com.example.simplepokedex.databinding.FragmentPokemonListBinding
import com.example.simplepokedex.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokemonListFragment : Fragment() {
    private lateinit var binding: FragmentPokemonListBinding
    private val viewModel : PokemonListViewModel by viewModels()

    @Inject
    lateinit var adapter : PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        viewModel.initializePokemonList()
        setupObservers()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter.setOnItemClickListener{
            val action = PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment(it)
            requireView().findNavController().navigate(action)
        }

        binding.pokemonListRecyclerView.adapter = adapter
        binding.pokemonListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPos == totalItemCount - 1 && !viewModel.isLoading) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            if (uiState.pokemonListUpdated) {
                uiState.pokemonList?.let {
                    adapter.appendDataSet(it)
                    viewModel.onUpdatedPokemonListHandled()
                }
            }
        }
    }
}