package com.example.poincareapp.data
import com.google.firebase.database.Exclude
//Data class for holding each record in the database
class Tipp(@get:Exclude
           var id: String? = null,
           var eventName: String? = null,
           var eventTipp: String? = null,
           var eventOdds: String? = null,
           var eventBankroll: String? = null,
           var eventType: String? = null,
           @get:Exclude
           var isDeleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean{
        return if(other is Tipp){
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (eventName?.hashCode() ?: 0)
        result = 31 * result + (eventTipp?.hashCode() ?: 0)
        result = 31 * result + (eventOdds?.hashCode() ?: 0)
        result = 31 * result + (eventBankroll?.hashCode() ?: 0)
        result = 31 * result + (eventType?.hashCode() ?: 0)
        result = 31 * result + isDeleted.hashCode()
        return result
    }

}