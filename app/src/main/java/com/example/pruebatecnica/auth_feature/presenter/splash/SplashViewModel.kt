package com.example.pruebatecnica.auth_feature.presenter.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.infraestructure.BaseViewModel
import com.example.pruebatecnica.organization_feature.domain.model.OrganizationData
import com.example.pruebatecnica.organization_feature.domain.usecase.GetOrganizationsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOrganizationsDataUseCase: GetOrganizationsData
):BaseViewModel() {

    private val _getData= MutableLiveData<ResponseState<OrganizationData>>()
    val getData: LiveData<ResponseState<OrganizationData>> get() = _getData

    init {
        downloadData()
    }

    private fun downloadData(){
        launchJob {
            val result=getOrganizationsDataUseCase()
            withContext(Dispatchers.Main){
                _getData.value=result
            }
        }
    }
}