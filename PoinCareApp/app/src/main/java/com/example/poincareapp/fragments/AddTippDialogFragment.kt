package com.example.poincareapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.poincareapp.R
import com.example.poincareapp.data.Tipp
import com.example.poincareapp.databinding.FragmentAddTippDialogBinding
import com.google.android.material.snackbar.Snackbar
//When the users taps on the plus button this dialog comes up
class AddTippDialogFragment : DialogFragment() {

    private var _binding: FragmentAddTippDialogBinding? = null
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
        _binding = FragmentAddTippDialogBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this).get(TippViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner,Observer{
            val message = if(it == null){
                getString(R.string.added_tipp)
            }
            else {
                getString(R.string.error,it.message)
            }
            Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT).show()
            dismiss()
        })



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
            val tipp = Tipp()
            tipp.eventName = eventName
            tipp.eventTipp = eventTipp
            tipp.eventOdds = eventOdds
            tipp.eventBankroll = eventBankroll
            tipp.eventType = eventType
            viewModel.addTipp(tipp)

            /*
            var dbRef = FirebaseDatabase.getInstance().getReference("NODE_TIPPS")
            val tippID = dbRef.push().key!!
            dbRef.child(tippID).setValue(tipp)*/
        }
    }
}