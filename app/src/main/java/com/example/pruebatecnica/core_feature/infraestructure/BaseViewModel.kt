package com.example.pruebatecnica.core_feature.infraestructure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private var job: Job? = null
    protected fun launchJob(block: suspend () -> Unit) {
        job = viewModelScope.launch(Dispatchers.IO) {
            block()
        }
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}