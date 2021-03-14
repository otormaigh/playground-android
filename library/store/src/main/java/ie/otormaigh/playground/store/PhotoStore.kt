package ie.otormaigh.playground.store

import android.database.sqlite.SQLiteConstraintException
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
) {

  suspend fun fetchPhotos(rover: String, camera: String, page: Int = 1, sol: Int = -1, earthData: String = ""): Flow<List<Photo>> {
    if (sol == -1 && earthData.isEmpty()) throw Exception("Field { sol } AND { earthDate } must not be empty.")

    val query = database.photoQueries

    try {
      val response = api.getPhotos(
        rover = rover,
        sol = sol,
        earth_date = earthData,
        camera = camera,
        page = page
      )

      response.photos.mapToDatabase().forEach {
        try {
          database.photoQueries.insert(it)
        } catch (e: SQLiteConstraintException) {
          Timber.e(e.localizedMessage)

          // TODO: Simpler way to handle PK conflicts.
          database.cameraQueries.upsert(it.camera.name, it.camera.rover_id, it.camera.full_name, id = it.id)
          database.photoQueries.upsert(it.sol, it.camera, it.img, it.earth_date, it.rover, id = it.id)
          database.roverQueries.upsert(it.rover.name, it.rover.landing_date, it.rover.launch_date, it.rover.status, id = it.rover.id)
        }
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