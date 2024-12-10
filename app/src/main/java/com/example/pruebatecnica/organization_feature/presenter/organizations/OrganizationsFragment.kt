package com.example.pruebatecnica.organization_feature.presenter.organizations

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.FragmentOrganizationsBinding
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganizationExpandable
import com.example.pruebatecnica.organization_feature.domain.model.Organization

class OrganizationsFragment : Fragment() {

    private var _binding:FragmentOrganizationsBinding?=null
    private val binding get() = _binding!!

    private val viewModel: OrganizationsViewModel by viewModels()

    val organizations = listOf(
        Organization(
            id = "1",
            dateInsert = "2024-12-10",
            slug = "org-slug-1",
            columns = "column1,column2",
            fact = "fact1",
            organization = "Organization A",
            resource = "resourceA",
            url = "https://exampleA.com",
            operations = "op1, op2",
            dataset = "dataset_A",
            createdAt = 1688875642000L
        ),
        Organization(
            id = "2",
            dateInsert = "2024-12-11",
            slug = "org-slug-2",
            columns = "column3,column4",
            fact = "fact2",
            organization = "Organization B",
            resource = "resourceB",
            url = "https://exampleB.com",
            operations = "op3, op4",
            dataset = "dataset_B",
            createdAt = 1688875643000L
        ),
        Organization(
            id = "3",
            dateInsert = "2024-12-12",
            slug = "org-slug-3",
            columns = "column5,column6",
            fact = "fact3",
            organization = "Organization C",
            resource = "resourceC",
            url = "https://exampleC.com",
            operations = "op5, op6",
            dataset = "dataset_C",
            createdAt = 1688875644000L
        ),
        Organization(
            id = "4",
            dateInsert = "2024-12-13",
            slug = "org-slug-4",
            columns = "column7,column8",
            fact = "fact4",
            organization = "Organization D",
            resource = "resourceD",
            url = "https://exampleD.com",
            operations = "op7, op8",
            dataset = "dataset_D",
            createdAt = 1688875645000L
        ),
        Organization(
            id = "5",
            dateInsert = "2024-12-14",
            slug = "org-slug-5",
            columns = "column9,column10",
            fact = "fact5",
            organization = "Organization E",
            resource = "resourceE",
            url = "https://exampleE.com",
            operations = "op9, op10",
            dataset = "dataset_E",
            createdAt = 1688875646000L
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentOrganizationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter=OrganizationsAdapter(organizations.toOrganizationExpandable())
        binding.lvOrganizations.adapter=adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}