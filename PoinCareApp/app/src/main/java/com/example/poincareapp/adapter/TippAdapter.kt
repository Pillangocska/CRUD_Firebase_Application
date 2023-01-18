package com.example.poincareapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.poincareapp.data.Tipp
import com.example.poincareapp.databinding.RecyclerViewTippBinding
import com.example.poincareapp.fragments.TippViewModel

// Tipp Adapter: bridge between UI component and data source that helps us to fill data in UI component
class TippAdapter(private val listener: TippRecordClickListener): RecyclerView.Adapter<TippAdapter.ViewHolder>(), ViewModelStoreOwner {
    private lateinit var viewModel: TippViewModel
    var tipps = mutableListOf<Tipp>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //ViewModelStore is not needed anymore delete it!!
        viewModel = ViewModelProvider(this)[TippViewModel::class.java]
        return ViewHolder(RecyclerViewTippBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tippRecord = tipps[position]

        holder.binding.textEvent.text = tipps[position].eventName
        holder.binding.textTipp.text = tipps[position].eventTipp
        holder.binding.textOdds.text = tipps[position].eventOdds
        holder.binding.textBankroll.text = tipps[position].eventBankroll
        holder.binding.textType.text = tipps[position].eventType

        holder.binding.deleteButton.setOnClickListener{
            //tipps.remove(tippRecord)
            listener.onItemDeleted(tippRecord)
            notifyDataSetChanged()
        }

        holder.binding.editButton.setOnClickListener{
            listener.onItemUpdated(tippRecord)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return tipps.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTipp(tipp: Tipp){
        if(!tipps.contains(tipp)){
            tipps.add(tipp)
        }
        else{
            val index = tipps.indexOf(tipp)
            if(tipp.isDeleted){
                tipps.removeAt(index)
            }
            else{
                tipps[index] = tipp
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RecyclerViewTippBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore()
    }

    interface TippRecordClickListener {
        fun onItemUpdated(item: Tipp)
        fun onItemDeleted(item: Tipp)
    }
}