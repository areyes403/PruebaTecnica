package com.example.pruebatecnica.core_feature.presenter.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.auth_feature.domain.model.AuthSession
import com.example.pruebatecnica.auth_feature.domain.usecase.ObserveSessionToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val observeSessionTokenUseCase: ObserveSessionToken
):ViewModel() {

    private val _sessionState= MutableStateFlow<AuthSession?>(null)
    val sessionState=_sessionState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        null)
        .onStart {
            getSession()
        }

    private val _tokenState= MutableStateFlow<String?>(null)
    val token:StateFlow<String?> get() = _tokenState

    private fun getSession() = viewModelScope.launch(Dispatchers.IO){
        observeSessionTokenUseCase().collect {
            _sessionState.value=it
        }
    }

    fun authenticated(){
        _tokenState.value=generateRandomToken()
    }

    private fun generateRandomToken(): String {
        return UUID.randomUUID().toString()
    }

}