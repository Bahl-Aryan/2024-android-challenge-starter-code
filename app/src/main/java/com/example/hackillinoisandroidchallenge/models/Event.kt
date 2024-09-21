package com.example.hackillinoisandroidchallenge.models

data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val eventType: String
)