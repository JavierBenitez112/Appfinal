package com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.KtorCoinApi
import com.uvg.ejercicioslabs.ejercicios.ktor.data.repository.MonsterRepositoryImpl
import com.uvg.ejercicioslabs.ejercicios.ktor.di.KtorDependencies
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.model.DataError
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.onError
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.onSuccess
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.repository.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonsterListViewModel(
    private val monsterRepository: CoinRepository
): ViewModel() {

    private val _state = MutableStateFlow(MonsterListState())
    val state = _state.asStateFlow()

    init {
        getMonsters()
    }

    fun getMonsters() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true,
                isGenericError = false,
                noInternetConnection = false
            )}

            monsterRepository
                .getAllMonsters()
                .onSuccess { monsters ->
                    // Exitoso
                    _state.update { it.copy(
                        isLoading = false,
                        data = monsters,
                        lastUpdated = monsters.firstOrNull()?.lastUpdated
                    ) }
                }
                .onError { error ->
                    if (error == DataError.NO_INTERNET) {
                        _state.update { it.copy(
                            isLoading = false,
                            noInternetConnection = true
                        ) }
                    } else {
                        _state.update { it.copy(
                            isLoading = false,
                            isGenericError = true,
                        ) }
                    }
                }
        }
    }

    fun viewOffline() {
        viewModelScope.launch {
            val localMonsters = monsterRepository.getAllMonsters()
            localMonsters.onSuccess { monsters ->
                _state.update { it.copy(
                    data = monsters,
                    lastUpdated = monsters.firstOrNull()?.lastUpdated
                ) }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = checkNotNull(this[APPLICATION_KEY])
                val api = KtorCoinApi(KtorDependencies.provideHttpClient())
                val db = KtorDependencies.provideLocalDb(
                    context = context
                )
                MonsterListViewModel(
                    monsterRepository = MonsterRepositoryImpl(
                        zeldaApi = api,
                        monsterDao = db.monsterDao()
                    )
                )
            }
        }
    }
}