package com.uvg.ejercicioslabs.ejercicios.ktor.presentation.monsterProfile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.uvg.ejercicioslabs.ejercicios.ktor.data.network.KtorCoinApi
import com.uvg.ejercicioslabs.ejercicios.ktor.data.repository.MonsterRepositoryImpl
import com.uvg.ejercicioslabs.ejercicios.ktor.di.KtorDependencies
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.onError
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.network.util.onSuccess
import com.uvg.ejercicioslabs.ejercicios.ktor.domain.repository.CoinRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonsterProfileViewModel(
    private val monsterRepository: CoinRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val monsterProfile = savedStateHandle.toRoute<MonsterProfileDestination>()

    private val _state = MutableStateFlow(MonsterProfileState())
    val state = _state.asStateFlow()

    init {
        getMonsterById()
    }

    private fun getMonsterById() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true,
            ) }
            delay(1000L)
            monsterRepository
                .getOneMonster(id = monsterProfile.id)
                .onSuccess { monster ->
                    _state.update { it.copy(
                        isLoading = false,
                        monster = monster,
                        isGenericError = false
                    ) }
                }
                .onError {
                    _state.update { it.copy(
                        isLoading = false,
                        isGenericError = true
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
                MonsterProfileViewModel(
                    monsterRepository = MonsterRepositoryImpl(
                        zeldaApi = api,
                        monsterDao = db.monsterDao()
                    ),
                    savedStateHandle = this.createSavedStateHandle()
                )
            }
        }
    }
}