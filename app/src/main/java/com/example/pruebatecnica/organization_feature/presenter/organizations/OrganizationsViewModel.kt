package com.example.pruebatecnica.organization_feature.presenter.organizations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pruebatecnica.core_feature.infraestructure.BaseViewModel
import com.example.pruebatecnica.core_feature.util.Constants.LIMIT_PAGINATION
import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganizationExpandable
import com.example.pruebatecnica.organization_feature.data.model.OrganizationExpandable
import com.example.pruebatecnica.organization_feature.data.model.PagerOptions
import com.example.pruebatecnica.organization_feature.domain.usecase.CheckLocalDataCount
import com.example.pruebatecnica.organization_feature.domain.usecase.GetOrganizationsListByNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationsViewModel @Inject constructor(
    private val getOrganizationsByNumberUseCase: GetOrganizationsListByNumber,
    private val checkLocalDataCount: CheckLocalDataCount
) : BaseViewModel() {

    private var count:Int=0

    private val _data=MutableLiveData<List<OrganizationExpandable>>()
    val data:LiveData<List<OrganizationExpandable>> get() = _data

    private val _pager=MutableLiveData<PagerOptions>()
    val pager:LiveData<PagerOptions> get() = _pager

    init {
        getData()
    }

    private fun getData(){
        launchJob {
            count=checkLocalDataCount()
            val totalPages = (count / LIMIT_PAGINATION) + if (count % LIMIT_PAGINATION > 0) 1 else 0
            _pager.postValue(calculatePagerOptions(1, totalPages))
        }
    }

    private fun calculatePagerOptions(currentPage: Int, totalPages: Int): PagerOptions {
        val first = (currentPage).toString()
        val second = (currentPage + 1).takeIf { it <= totalPages }?.toString() ?: ""
        val third = (currentPage + 2).takeIf { it <= totalPages }?.toString() ?: ""

        val previous = currentPage > 1
        val next = currentPage < totalPages

        return PagerOptions(
            previous  = previous,
            first = first,
            second = second,
            third = third,
            next = next,
            currentPage = currentPage
        )
    }

    fun nextPage() {
        val current = _pager.value?.currentPage ?: return
        val totalPages = (count / LIMIT_PAGINATION) + if (count % LIMIT_PAGINATION > 0) 1 else 0
        if (current < totalPages) {
            val newPage = current + 1
            _pager.value=calculatePagerOptions(newPage, totalPages)
        }
    }

    fun previousPage() {
        val current = _pager.value?.currentPage ?: return
        if (current > 1) {
            val newPage = current - 1
            _pager.value=calculatePagerOptions(newPage, (count / LIMIT_PAGINATION) + if (count % LIMIT_PAGINATION > 0) 1 else 0)
        }
    }

    fun searchData(currentPage: Int) {
        launchJob {
            _data.postValue(getOrganizationsByNumberUseCase(offset = (currentPage - 1) * LIMIT_PAGINATION).toOrganizationExpandable())
        }
    }

}