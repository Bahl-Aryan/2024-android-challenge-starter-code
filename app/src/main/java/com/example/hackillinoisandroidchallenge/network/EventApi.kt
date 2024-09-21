import retrofit2.http.GET
import retrofit2.Call
import com.example.hackillinoisandroidchallenge.models.Event

interface EventApiService {
    @GET("event/")
    fun getEvents(): Call<List<Event>>
}