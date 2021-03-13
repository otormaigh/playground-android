package ie.otormaigh.playground.networking.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NasaResponse(
  val photos: List<PhotoResponse>
)
