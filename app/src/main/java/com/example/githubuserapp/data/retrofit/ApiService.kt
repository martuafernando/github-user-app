import com.example.githubuserapp.data.response.UsersResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getRestaurant(
        @Query("q") query: String
    ): Call<UsersResponse>
}