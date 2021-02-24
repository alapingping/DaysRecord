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
    var code = MutableLiveData<Int>()

    fun getMessagesFromRemote() = viewModelScope.launch {
        val data = withContext(Dispatchers.IO) {
            Repository.getMessagesFromRemote()
        }
        if (data != null) {
            list.value = data
        }
    }

    fun addNewMessage(message: Message) = viewModelScope.launch {
        val resultCode = withContext(Dispatchers.IO) {
            Repository.addNewMessage(message)
        }
        code.value = resultCode
    }

}