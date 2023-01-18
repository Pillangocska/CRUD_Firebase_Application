package com.example.poincareapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.poincareapp.adapter.TippAdapter
import com.example.poincareapp.data.Tipp
import com.example.poincareapp.databinding.FragmentTipsBinding
import com.google.android.material.snackbar.Snackbar


class TipsFragment : Fragment(), TippAdapter.TippRecordClickListener {

    private var _binding: FragmentTipsBinding? = null
    private val binding get() = _binding!!

    private val adapter = TippAdapter(this)

    private lateinit var viewModel: TippViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTipsBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[TippViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewTipps.adapter = adapter

        binding.addButton.setOnClickListener{
            AddTippDialogFragment().show(childFragmentManager,"")
        }

        viewModel.tipp.observe(viewLifecycleOwner, Observer {
            adapter.addTipp(it)
        })

        viewModel.getRealtimeUpdate()

        //adapter.ViewHolder(binding = RecyclerViewTippBinding.bind(binding.root)).binding.deleteButton.setOnClickListener{}

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewTipps)
    }
    //What happens when we swipe on a record
    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            var currentTip = adapter.tipps[position]
            when(direction){
                ItemTouchHelper.RIGHT -> {
                    UpdateTippDialogFragment(currentTip).show(childFragmentManager,"")
                }
                ItemTouchHelper.LEFT -> {
                    AlertDialog.Builder(requireContext()).also {
                        it.setTitle("Are you sure to delete this record?")
                        it.setPositiveButton("Yes"){dialog,which ->
                            viewModel.deleteTipp(currentTip)
                            binding.recyclerViewTipps.adapter?.notifyItemRemoved(position)
                            Snackbar.make(binding.root,"Record has been deleted",Snackbar.LENGTH_SHORT).show()
                        }
                    }.create().show()
                }
            }
            binding.recyclerViewTipps.adapter?.notifyDataSetChanged()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemUpdated(item: Tipp) {
        UpdateTippDialogFragment(item).show(childFragmentManager,"")
    }

    override fun onItemDeleted(item: Tipp) {
        viewModel.deleteTipp(item)
        Snackbar.make(binding.root,"Record has been deleted",Snackbar.LENGTH_SHORT).show()
    }
}