package ie.otormaigh.playground.networking.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoverResponse(
  val id: Int,
  val name: String,
  @Json(name = "landing_date")
  val landingDate: String,
  @Json(name = "launch_date")
  val launchDate: String,
  val status: String
)