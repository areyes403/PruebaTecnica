package com.example.pruebatecnica.auth_feature.presenter.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.auth_feature.domain.usecase.SignIn
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.infraestructure.BaseViewModel
import com.example.pruebatecnica.organization_feature.domain.usecase.GetOrganizationsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignIn
):BaseViewModel() {
    private val _response=MutableLiveData<ResponseState<Unit>>()
    val response:LiveData<ResponseState<Unit>> get() = _response

    fun login(credentials:AuthCredentials){
        launchJob {
            val result=signInUseCase(credentials = credentials)
            withContext(Dispatchers.Main){
                _response.value=result
            }
        }
    }

}