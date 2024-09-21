import retrofit2.http.GET
import retrofit2.Call
import com.example.hackillinoisandroidchallenge.models.Event
import com.example.hackillinoisandroidchallenge.network.EventResponse

interface EventApiService {
    @GET("event/")
    fun getEvents(): Call<EventResponse>
}