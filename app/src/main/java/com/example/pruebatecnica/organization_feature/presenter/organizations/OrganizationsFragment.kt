package com.example.pruebatecnica.organization_feature.presenter.organizations

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pruebatecnica.R
import com.example.pruebatecnica.core_feature.util.checkPermissionGranted
import com.example.pruebatecnica.core_feature.util.invisible
import com.example.pruebatecnica.core_feature.util.show
import com.example.pruebatecnica.core_feature.util.snackBar
import com.example.pruebatecnica.core_feature.util.toast
import com.example.pruebatecnica.databinding.FragmentOrganizationsBinding
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganization
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganizationExpandable
import com.example.pruebatecnica.organization_feature.data.model.OrganizationExpandable
import com.example.pruebatecnica.organization_feature.domain.model.Organization
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationsFragment : Fragment(),OnClickListener {

    private var _binding:FragmentOrganizationsBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<OrganizationsViewModel>()

    private var filteredList = listOf<OrganizationExpandable>()

    private val adapter=TestListAdapter(this)

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var selected:Organization

    private val requiredPermissions= arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getLastLocation { lat, lng ->
                    copyToClipboard(lat,lng)
                }
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getLastLocation { lat, lng ->
                    copyToClipboard(lat,lng)
                }
            }
            else -> {
                snackBar("Enable location permission.")
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentOrganizationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

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
                    val query = editable.toString().trim()
                    val tempList=filteredList
                    filteredList = if (query.isEmpty()) {
                        tempList.map { org->
                            org.copy(isFiltering = true)
                        }
                    } else {
                        val filtered=tempList.filter {
                            it.organization.contains(query,ignoreCase = true)
                        }.map { org->
                            org.copy(isFiltering = true)
                        }
                        filtered
                    }
                    adapter.submitList(filteredList)
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
    override fun shareItem(item: OrganizationExpandable) {
        selected=item.toOrganization()
        when{
            checkPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)->{
                getLastLocation { lat, lng ->
                    copyToClipboard(lat,lng)
                }
            }

            checkPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)->{
                getLastLocation { lat, lng ->
                    copyToClipboard(lat,lng)
                }
            }
            else->{
                locationPermissionRequest.launch(requiredPermissions)
            }
        }
    }

    private fun copyToClipboard(latitude:Double,longitude:Double){
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", selected.toString()+"Lat:$latitude,Lng:$longitude")
        clipboard.setPrimaryClip(clip)
        toast("Text copied to clipboard!")
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(lastLocation: (Double, Double) -> Unit){
        fusedLocationClient.lastLocation.addOnSuccessListener { loc->
            lastLocation(loc.latitude,loc.longitude)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}

interface OnClickListener{
    fun shareItem(item:OrganizationExpandable)
}