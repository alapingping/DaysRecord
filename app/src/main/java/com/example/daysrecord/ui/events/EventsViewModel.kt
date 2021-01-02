package com.example.daysrecord.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.logic.repository.Repository

class EventsViewModel : ViewModel() {

    private val recordsLiveData = MutableLiveData<List<Record>>()

    val records: LiveData<List<Record>> = Transformations.switchMap(recordsLiveData) {
        list -> Repository.getAllRecords()
    }

    fun getAllRecords() {
        recordsLiveData.value = recordsLiveData.value
    }

}