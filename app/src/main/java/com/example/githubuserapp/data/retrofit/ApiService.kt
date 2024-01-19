import com.example.githubuserapp.data.response.UserDetail
import com.example.githubuserapp.data.response.RelatedUser
import com.example.githubuserapp.data.response.UsersResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<RelatedUser>>

    @GET("users/{username}/following")
    fun getFollowings(
        @Path("username") username: String
    ): Call<List<RelatedUser>>
}