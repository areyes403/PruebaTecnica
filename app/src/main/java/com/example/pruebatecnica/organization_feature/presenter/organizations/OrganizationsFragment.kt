package com.example.pruebatecnica.organization_feature.presenter.organizations

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
            id = "5818ede7bb681ad20c18e617",
            dateInsert = "2016-11-01T19:32:55.363Z",
            slug = "cdi",
            columns = "NA",
            fact = "15 Es el número de bases de datos que la dependencia CDI ha publicado. ",
            organization = "CDI",
            resource = "Número de bases de datos",
            url = "http://datos.gob.mx/busca/organization/cdi",
            operations = "Rutina de R",
            dataset = "Número de bases de datos",
            createdAt = 1688875642000L
        ),
        Organization(
            id = "5818ede7bb681ad20c18e617",
            dateInsert = "2016-11-01T19:32:55.363Z",
            slug = "cdi",
            columns = "NA",
            fact = "15 Es el número de bases de datos que la dependencia CDI ha publicado. ",
            organization = "CDI",
            resource = "Número de bases de datos",
            url = "http://datos.gob.mx/busca/organization/cdi",
            operations = "Rutina de R",
            dataset = "Número de bases de datos",
            createdAt = 1688875642000L
        ),
        Organization(
            id = "5818ede7bb681ad20c18e617",
            dateInsert = "2016-11-01T19:32:55.363Z",
            slug = "cdi",
            columns = "NA",
            fact = "15 Es el número de bases de datos que la dependencia CDI ha publicado. ",
            organization = "CDI",
            resource = "Número de bases de datos",
            url = "http://datos.gob.mx/busca/organization/cdi",
            operations = "Rutina de R",
            dataset = "Número de bases de datos",
            createdAt = 1688875642000L
        ),
        Organization(
            id = "5818ede7bb681ad20c18e617",
            dateInsert = "2016-11-01T19:32:55.363Z",
            slug = "cdi",
            columns = "NA",
            fact = "15 Es el número de bases de datos que la dependencia CDI ha publicado. ",
            organization = "CDI",
            resource = "Número de bases de datos",
            url = "http://datos.gob.mx/busca/organization/cdi",
            operations = "Rutina de R",
            dataset = "Número de bases de datos",
            createdAt = 1688875642000L
        ),
        Organization(
            id = "5818ede7bb681ad20c18e617",
            dateInsert = "2016-11-01T19:32:55.363Z",
            slug = "cdi",
            columns = "NA",
            fact = "15 Es el número de bases de datos que la dependencia CDI ha publicado. ",
            organization = "CDI",
            resource = "Número de bases de datos",
            url = "http://datos.gob.mx/busca/organization/cdi",
            operations = "Rutina de R",
            dataset = "Número de bases de datos",
            createdAt = 1688875642000L
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
        binding.lvOrganizations.layoutManager= LinearLayoutManager(context)
        binding.lvOrganizations.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}