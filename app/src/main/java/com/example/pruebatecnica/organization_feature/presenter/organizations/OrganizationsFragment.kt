package com.example.pruebatecnica.organization_feature.presenter.organizations

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pruebatecnica.R
import com.example.pruebatecnica.core_feature.util.invisible
import com.example.pruebatecnica.core_feature.util.show
import com.example.pruebatecnica.databinding.FragmentOrganizationsBinding
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganizationExpandable
import com.example.pruebatecnica.organization_feature.data.model.OrganizationExpandable
import com.example.pruebatecnica.organization_feature.domain.model.Organization
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationsFragment : Fragment() {

    private var _binding:FragmentOrganizationsBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<OrganizationsViewModel>()

    private var filteredList = listOf<OrganizationExpandable>()
    private val adapter=TestListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentOrganizationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvOrganizationsFilter.adapter=adapter
            rvOrganizations.adapter=adapter

            searchView.setupWithSearchBar(searchBar)

            searchBar.setOnClickListener {
                if(!searchView.isShowing){
                    searchView.show()
                }
            }

            searchView.editText.setOnEditorActionListener { v, actionId, event ->
                val text = v?.text.toString()
                searchView.hide()
                searchBar.setText(text)
                adapter.submitList(filteredList)
                true
            }

            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(editable: Editable?) {
//                    val query = editable.toString().trim()
//                    filteredList = if (query.isEmpty()) {
//                        organizations.map { org->
//                            org.copy(isFiltering = true)
//                        }
//                    } else {
//                        val filtered=organizations.filter {
//                            it.organization.contains(query,ignoreCase = true)
//                        }.map { org->
//                            org.copy(isFiltering = true)
//                        }
//                        filtered
//                    }
//                    adapter.submitList(filteredList)
                }
            })
        }

        binding.imgNext.setOnClickListener {
            viewModel.nextPage()
        }
        binding.imgPrevious.setOnClickListener {
            viewModel.previousPage()
        }

        observers()
    }

    private fun observers(){
        viewModel.data.observe(viewLifecycleOwner){ list->
            filteredList=list
            adapter.submitList(filteredList)
        }
        viewModel.pager.observe(viewLifecycleOwner){pager->
            viewModel.searchData(pager.currentPage)
            binding.apply {
                pager.currentPage
                txtFirst.text=pager.first
                txtSecond.text=pager.second
                txThirt.text=pager.third
                if (!pager.previous) imgPrevious.invisible() else imgPrevious.show()
                if (!pager.next) imgNext.invisible() else imgNext.show()
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}