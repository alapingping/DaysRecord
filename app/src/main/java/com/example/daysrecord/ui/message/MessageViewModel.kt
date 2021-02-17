package com.example.daysrecord.ui.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daysrecord.logic.model.Message
import com.example.daysrecord.logic.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessageViewModel : ViewModel()  {

    var list = MutableLiveData<List<Message>>()

    fun getMessagesFromRemote() = viewModelScope.launch {
        val data = withContext(Dispatchers.IO) {
            Repository.getMessagesFromRemote()
        }
        if (data != null) {
            list.value = data
        }
    }

}