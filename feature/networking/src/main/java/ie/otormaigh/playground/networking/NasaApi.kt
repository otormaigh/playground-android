package ie.otormaigh.playground.networking

import ie.otormaigh.playground.networking.response.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApi {
  @GET("{rover}/photos")
  suspend fun getPhotos(
    @Path("rover") rover: String,
    @Query("sol") sol: Int,
    @Query("earth_date") earth_date: String,
    @Query("camera") camera: String,
    @Query("page") page: Int,
    @Query("api_key") api_key: String
  ): List<PhotoResponse>
}