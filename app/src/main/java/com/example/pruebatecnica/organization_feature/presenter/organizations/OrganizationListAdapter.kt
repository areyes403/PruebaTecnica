package com.example.pruebatecnica.organization_feature.presenter.organizations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.databinding.ItemCardOrganizationBinding
import com.example.pruebatecnica.organization_feature.data.model.OrganizationExpandable

class TestListAdapter(
    private val onClickListener: OnClickListener
):ListAdapter<OrganizationExpandable,TestListAdapter.ViewHolder>(ExpenseDiffCallback()) {

    inner class ViewHolder(private val binding: ItemCardOrganizationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: OrganizationExpandable) {
            binding.apply {
                if (model.isFiltering){
                    expandableContent.visibility=View.VISIBLE
                }else{
                    val visibility=model.isExpanded
                    if (visibility){
                        expandableContent.visibility=View.VISIBLE
                    }else{
                        expandableContent.visibility=View.GONE
                    }
                }
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
                    model.isExpanded=!model.isExpanded
                    notifyItemChanged(position)
                }
                imgShare.setOnClickListener {
                    onClickListener.shareItem(model)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemCardOrganizationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj=getItem(position)
        holder.bind(obj)
    }
}

class ExpenseDiffCallback : DiffUtil.ItemCallback<OrganizationExpandable>() {
    override fun areItemsTheSame(oldItem: OrganizationExpandable, newItem: OrganizationExpandable): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: OrganizationExpandable, newItem: OrganizationExpandable): Boolean {
        return oldItem == newItem
    }
}