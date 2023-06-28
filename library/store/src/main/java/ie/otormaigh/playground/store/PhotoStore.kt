package ie.otormaigh.playground.store

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import ie.otormaigh.playground.Camera
import ie.otormaigh.playground.Photo
import ie.otormaigh.playground.Rover
import ie.otormaigh.playground.networking.NasaApi
import ie.otormaigh.playground.networking.response.PhotoResponse
import ie.otormaigh.playground.sqldelight.Database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoStore
@Inject
constructor(
  val api: NasaApi,
  private val database: Database
) : Store() {

  suspend fun fetchPhotos(rover: String, sol: Int? = null, earthDate: String? = null, camera: String? = null, page: Int = 1): Flow<List<Photo>> {
    if (sol == null && earthDate == null) throw Exception("Field { sol } AND { earthDate } must not be empty.")

    val query = database.photoQueries

    try {
      val response = api.getPhotos(
        rover = rover,
        sol = sol, // TODO: Calculate sol from earth_date
        earth_date = earthDate,
        camera = camera,
        page = page
      )

      response.photos.mapToDatabase().forEach {
        database.photoQueries.insertOrReplace(it)
      }

      return query.selectAll().asFlow().mapToList()
    } catch (e: Exception) {
      Timber.e(e)
      // TODO : Error parsing
      when (e) {
        is HttpException -> Timber.e(e)
        else -> Timber.e(e)
      }
    }
    return emptyFlow()
  }
}

private fun List<PhotoResponse>.mapToDatabase(): List<Photo> = map { response ->
  Photo(
    id = response.id,
    sol = response.sol,
    camera = Camera(
      id = response.camera.id,
      name = response.camera.name,
      rover_id = response.camera.roverId,
      full_name = response.camera.fullName,
    ),
    img = response.img,
    earth_date = response.earthDate,
    rover = Rover(
      id = response.rover.id,
      name = response.rover.name,
      landing_date = response.rover.landingDate,
      launch_date = response.rover.launchDate,
      status = response.rover.status
    )
  )
}