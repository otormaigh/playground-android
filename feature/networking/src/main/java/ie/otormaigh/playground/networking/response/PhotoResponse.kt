package ie.otormaigh.playground.networking.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoResponse(
  val id: Int,
  val sol: Int,
  val camera: CameraResponse,
  @Json(name = "img_src")
  val img: String,
  @Json(name = "earth_date")
  val earthDate: String,
  val rover: RoverResponse
)