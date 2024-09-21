package com.example.hackillinoisandroidchallenge

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.hackillinoisandroidchallenge.adapter.ViewPagerAdapter
import com.example.hackillinoisandroidchallenge.viewmodel.EventViewModel

class MainActivity : FragmentActivity() {

    private val eventViewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)

        eventViewModel.groupedEvents.observe(this, Observer { groupedEvents ->
            val adapter = ViewPagerAdapter(this, groupedEvents)
            viewPager.adapter = adapter

            // Link TabLayout and ViewPager2
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                val day = adapter.getDay(position)
                tab.text = day.toString() // Show the date on the tab
            }.attach()
        })

        eventViewModel.fetchEvents() // Fetch events and group by day
    }
}
