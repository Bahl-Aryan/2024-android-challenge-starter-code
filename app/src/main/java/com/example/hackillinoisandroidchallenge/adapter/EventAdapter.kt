import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackillinoisandroidchallenge.R
import com.example.hackillinoisandroidchallenge.models.Event
import com.squareup.picasso.Picasso
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.Instant

class EventAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: TextView = itemView.findViewById(R.id.eventName)
        val eventDescription: TextView = itemView.findViewById(R.id.eventDescription)
        val eventStartTime: TextView = itemView.findViewById(R.id.eventStartTime)
        val eventEndTime: TextView = itemView.findViewById(R.id.eventEndTime)
        val eventType: TextView = itemView.findViewById(R.id.eventType)
        val eventLocation: TextView = itemView.findViewById(R.id.eventLocation)
        val mapImageView: ImageView = itemView.findViewById(R.id.mapImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]

        // Set event name and description
        holder.eventName.text = event.name
        holder.eventDescription.text = event.description

        // Format and display start and end times
        holder.eventStartTime.text = "Start: ${formatUnixTimestamp(event.startTime)}"
        holder.eventEndTime.text = "End: ${formatUnixTimestamp(event.endTime)}"

        // Set event type and location
        holder.eventType.text = event.eventType

        if (event.locations.isNotEmpty()) {
            val location = event.locations[0]
            holder.eventLocation.text = "Location: ${location.description}"

            holder.eventLocation.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${location.latitude},${location.longitude}?q=${Uri.encode(location.description)}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                holder.itemView.context.startActivity(mapIntent)
            }
        }

        // Load map image using Picasso or Glide
        event.mapImageUrl?.let { mapUrl ->
            Picasso.get().load(mapUrl).into(holder.mapImageView)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    // Private helper function for formatting timestamps
    private fun formatUnixTimestamp(unixTime: Long): String {
        val instant = Instant.ofEpochSecond(unixTime)
        val zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()) // Convert to system's local time
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a") // Example: "Mar 21, 2024 10:00 AM"
        return zdt.format(formatter)
    }
}

