package com.example.pruebatecnica.core_feature.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.auth_feature.domain.usecase.ObserveSessionToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val observeSessionTokenUseCase: ObserveSessionToken
):ViewModel() {

    private val _tokenState= MutableStateFlow<String?>(null)
    val tokenState=_tokenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        null)
        .onStart {
            getSession()
        }

    private fun getSession() = viewModelScope.launch(Dispatchers.IO){
        observeSessionTokenUseCase().collect {
            _tokenState.value=it
        }
    }

}