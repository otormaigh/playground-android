package ie.otormaigh.playground.networking.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CameraResponse(
  val id: Long,
  val name: String,
  @Json(name = "rover_id")
  val roverId: Long,
  @Json(name = "full_name")
  val fullName: String
)