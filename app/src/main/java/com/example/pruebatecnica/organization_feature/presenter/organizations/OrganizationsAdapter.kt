package com.example.pruebatecnica.organization_feature.presenter.organizations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.databinding.ItemCardOrganizationBinding
import com.example.pruebatecnica.organization_feature.data.model.OrganizationExpandable
import com.example.pruebatecnica.organization_feature.domain.model.Organization
import java.util.Locale

class OrganizationsAdapter(private val data:List<OrganizationExpandable>):RecyclerView.Adapter<OrganizationsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:ItemCardOrganizationBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemCardOrganizationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            if (payloads[0] == 0) {
                holder.binding.expandableContent.visibility = View.GONE
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model=data[position]
        val isExpandable=data[position].isExpanded
        holder.binding.apply {
            expandableContent.visibility = if (isExpandable) View.VISIBLE else View.GONE
            txtFact.text=model.fact
            txtOrganization.text="Organizacion: ${model.organization}"
            txtSlug.text="Slug: ${model.slug}"
            txtId.text="ID: ${model.id}"
            txtColumns.text="Columns: ${model.columns}"
            txtResource.text="Resource: ${model.resource}"
            txtUrl.text="URL: ${model.url}"
            txtOperations.text="Operations: ${model.operations}"
            txtDataset.text="Dataset: ${model.dataset}"
            txtDateInsert.text="Inserted: ${model.dateInsert}"
            txtCreatedAt.text="Created: ${model.createdAt}"
            visibleLayout.setOnClickListener {
                toggleItemExpansion(position)
            }
        }
    }

    private fun toggleItemExpansion(position: Int) {
        val currentlyExpandedItem = data.indexOfFirst { it.isExpanded }
        if (currentlyExpandedItem >= 0 && currentlyExpandedItem != position) {
            data[currentlyExpandedItem].isExpanded = false
            notifyItemChanged(currentlyExpandedItem)
        }
        val item = data[position]
        item.isExpanded = !item.isExpanded
        notifyItemChanged(position)
    }

}