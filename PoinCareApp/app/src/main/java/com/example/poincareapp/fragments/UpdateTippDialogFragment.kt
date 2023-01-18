package com.example.poincareapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.poincareapp.data.Tipp
import com.example.poincareapp.databinding.FragmentUpdateTippDialogBinding
import com.google.android.material.snackbar.Snackbar
// Same as the AddTipp equivalent but we are updating a single record
class UpdateTippDialogFragment(private val tipp: Tipp) : DialogFragment() {

    private var _binding: FragmentUpdateTippDialogBinding? = null
    private val binding get() = _binding!!
    //Create object of tippview model to send the data to firebase
    private lateinit var viewModel: TippViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateTippDialogBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this).get(TippViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextEventName.setText(tipp.eventName)
        binding.editTextEventTipp.setText(tipp.eventTipp)
        binding.editTextEventOdds.setText(tipp.eventOdds)
        binding.editTextEventBankroll.setText(tipp.eventBankroll)
        binding.editTextEventType.setText(tipp.eventType)

        binding.buttonAdd.setOnClickListener {
            val eventName = binding.editTextEventName.text.toString().trim()
            val eventTipp = binding.editTextEventTipp.text.toString().trim()
            val eventOdds = binding.editTextEventOdds.text.toString().trim()
            val eventBankroll = binding.editTextEventBankroll.text.toString().trim()
            val eventType = binding.editTextEventType.text.toString().trim()

            if(eventName.isEmpty()){
                binding.editTextEventName.error = "This field is required"
                return@setOnClickListener
            }
            if(eventTipp.isEmpty()){
                binding.editTextEventTipp.error = "This field is required"
                return@setOnClickListener
            }
            if(eventOdds.isEmpty()){
                binding.editTextEventOdds.error = "This field is required"
                return@setOnClickListener
            }
            if(eventBankroll.isEmpty()){
                binding.editTextEventBankroll.error = "This field is required"
                return@setOnClickListener
            }
            if(eventType.isEmpty()){
                binding.editTextEventType.error = "This field is required"
                return@setOnClickListener
            }

            //We send our tipp object through our send method
            tipp.eventName = eventName
            tipp.eventTipp = eventTipp
            tipp.eventOdds = eventOdds
            tipp.eventBankroll = eventBankroll
            tipp.eventType = eventType
            viewModel.updateTipp(tipp)
            dismiss()
            Snackbar.make(binding.root,"Record has been updated",Snackbar.LENGTH_SHORT).show()
        }
    }
}