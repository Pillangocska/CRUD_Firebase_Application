package com.example.poincareapp.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poincareapp.data.Tipp
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
//This class is responsible for reading, adding, updating, deleting data from firebase
class TippViewModel: ViewModel() {
    private val dbtipps = FirebaseDatabase.getInstance().getReference("NODE_TIPPS")
    //We store the exception to refer to it later
    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result
    private val _tipp = MutableLiveData<Tipp>()
    val tipp: LiveData<Tipp> get() = _tipp

    fun addTipp(tipp: Tipp){
        //Passing the tipp data to firebase
        tipp.id = dbtipps.push().key
        dbtipps.child(tipp.id!!).setValue(tipp).addOnCompleteListener{
            if(it.isSuccessful){
                _result.value = null
            }
            else {
                _result.value = it.exception
            }
        }
    }

    private val childEventListener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val tipp = snapshot.getValue(Tipp::class.java)
            tipp?.id = snapshot.key
            _tipp.value = tipp!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val tipp = snapshot.getValue(Tipp::class.java)
            tipp?.id = snapshot.key
            _tipp.value = tipp!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val tipp = snapshot.getValue(Tipp::class.java)
            tipp?.id = snapshot.key
            tipp?.isDeleted = true
            _tipp.value = tipp!!
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    fun getRealtimeUpdate(){
        dbtipps.addChildEventListener(childEventListener)
    }

    fun updateTipp(tipp: Tipp){
        dbtipps.child(tipp.id!!).setValue(tipp).addOnCompleteListener{
            if(it.isSuccessful){
                _result.value = null
            }
            else {
                _result.value = it.exception
            }
        }
    }

    fun deleteTipp(tipp: Tipp){
        dbtipps.child(tipp.id!!).setValue(null).addOnCompleteListener{
            if(it.isSuccessful){
                _result.value = null
            }
            else{
                _result.value = it.exception
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        dbtipps.removeEventListener(childEventListener)
    }
}