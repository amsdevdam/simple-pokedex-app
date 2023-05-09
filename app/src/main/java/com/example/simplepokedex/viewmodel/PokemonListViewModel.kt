package com.example.simplepokedex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplepokedex.model.Pokemon
import com.example.simplepokedex.repository.PokemonRepository
import com.example.simplepokedex.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var _isLoading: Boolean = false
    val isLoading get() = _isLoading
    private var pageNumber: Int = 0
    private var isInitialized = false

    data class UiState(
        var pokemonList: List<Pokemon>? = null,
        var pokemonListUpdated: Boolean = false,
    )

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun loadNextPage() {
        pageNumber++
        loadNewData(pageNumber)
    }

    fun initializePokemonList() {
        if (!isInitialized) {
            loadNewData(0)
            isInitialized = true
        }
    }

    private fun loadNewData(pageNumber: Int) {
        _isLoading = true
        viewModelScope.launch(dispatcherProvider.getIoDispatcher()) {
            pokemonRepository.getPokemon(pageNumber)?.let {
                updatePokemonList(it)
            } ?: run {
                Log.e("PokemonListViewModel", "Error loading pokemon!")
            }
        }
    }

    private fun updatePokemonList(pokemonList: List<Pokemon>) {
        _uiState.postValue(
            _uiState.value?.copy(pokemonList = pokemonList, pokemonListUpdated = true) ?: UiState(
                pokemonList,
                true
            )
        )
    }

    fun onUpdatedPokemonListHandled() {
        _uiState.value = _uiState.value?.copy(pokemonListUpdated = false)
        _isLoading = false
    }
}