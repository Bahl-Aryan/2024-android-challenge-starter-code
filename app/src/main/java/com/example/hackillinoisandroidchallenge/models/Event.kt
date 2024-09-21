package com.example.hackillinoisandroidchallenge.models

data class Event(
    val eventId: String,
    val name: String,
    val description: String,
    val startTime: Long,   // Timestamps
    val endTime: Long,     // Timestamps
    val locations: List<Location>,  // Nested locations
    val eventType: String,
    val points: Int,
    val isAsync: Boolean,
    val mapImageUrl: String?
)

data class Location(
    val description: String,
    val latitude: Double,
    val longitude: Double
)
