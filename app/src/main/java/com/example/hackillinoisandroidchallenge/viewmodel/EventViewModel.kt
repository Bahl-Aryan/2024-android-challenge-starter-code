package com.example.hackillinoisandroidchallenge.viewmodel

import RetrofitInstance
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackillinoisandroidchallenge.models.Event
import kotlinx.coroutines.launch
import retrofit2.await

class EventViewModel : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getEvents().await()
                _events.postValue(response)
            } catch (e: Exception) {
                print(e)
            }
        }
    }
}