package com.example.hackillinoisandroidchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackillinoisandroidchallenge.models.Event
import kotlinx.coroutines.launch
import retrofit2.await
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class EventViewModel : ViewModel() {

    // LiveData to hold the list of events
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    // LiveData to hold grouped events by day
    private val _groupedEvents = MutableLiveData<Map<LocalDate, List<Event>>>()
    val groupedEvents: LiveData<Map<LocalDate, List<Event>>> get() = _groupedEvents

    // LiveData to handle errors (optional)
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Function to fetch events
    fun fetchEvents() {
        viewModelScope.launch {
            try {
                // Fetch events using Retrofit
                val response = RetrofitInstance.api.getEvents().await()

                // Post the raw list of events
                _events.postValue(response.events)

                // Group events by day and post to LiveData
                val groupedEventsMap = groupEventsByDay(response.events)
                _groupedEvents.postValue(groupedEventsMap)

            } catch (e: Exception) {
                // Handle any errors that occur during the API call
                e.printStackTrace()
                _error.postValue("Error fetching events: ${e.message}")
            }
        }
    }

    // Function to group events by day (LocalDate)
    private fun groupEventsByDay(events: List<Event>): Map<LocalDate, List<Event>> {
        return events.groupBy { event ->
            val instant = Instant.ofEpochSecond(event.startTime) // Unix timestamp to Instant
            val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            zonedDateTime.toLocalDate() // Extract the date part (LocalDate)
        }
    }
}

