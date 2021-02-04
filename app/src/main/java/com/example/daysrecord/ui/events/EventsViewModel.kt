package com.example.daysrecord.ui.events

import androidx.lifecycle.*
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.logic.repository.Repository
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

    private var recordsLiveData = MutableLiveData<List<Record>>()

    var records: LiveData<List<Record>> = Transformations.switchMap(recordsLiveData) {
        Repository.getAllRecords()
    }

    fun getAllRecords()  = viewModelScope.launch{
        recordsLiveData.value = recordsLiveData.value
    }

}