package com.example.daysrecord.ui.events

import androidx.lifecycle.*
import com.example.daysrecord.database.entity.Record
import com.example.daysrecord.logic.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsViewModel : ViewModel() {

    private val _record = MutableLiveData<String>()

    val records: LiveData<List<Record>> = Transformations.switchMap(_record) {
        Repository.getAllRecords()
    }

    fun getAllRecords()  = viewModelScope.launch{
        _record.value = _record.value
    }

    fun getAllRecordsByTitle(title: String)  = viewModelScope.launch{
        _record.value = title
    }

}