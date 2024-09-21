package com.example.hackillinoisandroidchallenge.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackillinoisandroidchallenge.models.Event
import com.example.hackillinoisandroidchallenge.models.EventFragment
import java.time.LocalDate

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val groupedEvents: Map<LocalDate, List<Event>>
) : FragmentStateAdapter(fragmentActivity) {

    private val days = groupedEvents.keys.toList()

    override fun getItemCount(): Int {
        return days.size
    }

    override fun createFragment(position: Int): Fragment {
        val day = days[position]
        val eventsForDay = groupedEvents[day] ?: emptyList()
        return EventFragment(eventsForDay)
    }

    fun getDay(position: Int): LocalDate {
        return days[position]
    }
}
