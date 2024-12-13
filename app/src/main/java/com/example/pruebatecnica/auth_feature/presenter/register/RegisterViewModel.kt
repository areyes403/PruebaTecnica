package com.example.pruebatecnica.auth_feature.presenter.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pruebatecnica.auth_feature.data.model.RegisterUser
import com.example.pruebatecnica.auth_feature.domain.usecase.SignUp
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.infraestructure.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUp
):BaseViewModel() {

    private val _registerState=MutableLiveData<ResponseState<Unit>>()
    val registerState:LiveData<ResponseState<Unit>> get() = _registerState

    fun register(data:RegisterUser){
        launchJob {
            val result= signUpUseCase(credentials = data)
            withContext(Dispatchers.Main){
                _registerState.value=result
            }
        }
    }
}