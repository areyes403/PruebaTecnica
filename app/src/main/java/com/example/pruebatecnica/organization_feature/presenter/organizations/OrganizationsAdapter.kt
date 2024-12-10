package com.example.pruebatecnica.organization_feature.presenter.organizations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pruebatecnica.databinding.ItemCardOrganizationBinding
import com.example.pruebatecnica.organization_feature.data.model.OrganizationExpandable
import com.example.pruebatecnica.organization_feature.domain.model.Organization

class OrganizationsAdapter(private val data:List<OrganizationExpandable>):BaseAdapter() {

    private val expandedPositions = mutableSetOf<Int>()

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long =position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding:ItemCardOrganizationBinding
        val view: View

        if (convertView == null) {
            binding = ItemCardOrganizationBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            view = binding.root
        } else {
            binding = ItemCardOrganizationBinding.bind(convertView)
            view = convertView
        }

        val currentItem = getItem(position) as OrganizationExpandable

        val isExpandable=data[position].isExpanded
        binding.expandableContent.visibility=if (isExpandable) View.VISIBLE else View.GONE


        binding.visibleLayout.setOnClickListener {
        }


        binding.apply {
            txtFact.text=currentItem.fact
            txtOrganization.text=currentItem.organization
            txtSlug.text=currentItem.slug
        }

        return view
    }

//    private fun isAnyItemExpanded(position: Int) {
//        val temp = data.indexOfFirst { it.isExpanded }
//        if (temp>=0 && temp!=position){
//            data[position].isExpanded=false
//            notifyDataSetChanged()
//        }
//    }
}