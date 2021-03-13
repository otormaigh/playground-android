package ie.otormaigh.playground.networking

import ie.otormaigh.playground.networking.response.NasaResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApi {
  @GET("{rover}/photos")
  suspend fun getPhotos(
    @Path("rover") rover: String? = null,
    @Query("sol") sol: Int? = null,
    @Query("earth_date") earth_date: String? = null,
    @Query("camera") camera: String? = null,
    @Query("page") page: Int? = null,
    @Query("api_key") api_key: String
  ): NasaResponse
}