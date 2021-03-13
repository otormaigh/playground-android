package ie.otormaigh.playground.networking.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CameraResponse(
  val id: Int,
  val name: String,
  @Json(name = "rover_id")
  val roverId: Int,
  @Json(name = "full_name")
  val fullName: String
)