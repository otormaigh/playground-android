package ie.otormaigh.playground.persistence.columnadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.ColumnAdapter
import ie.otormaigh.playground.Camera

//FIXME: Add actual parsers here.
class CameraColumnAdapter(moshi: Moshi) : ColumnAdapter<Camera, String> {
  private val adapter: JsonAdapter<Camera> = moshi.adapter(Camera::class.java)

  override fun decode(databaseValue: String): Camera = adapter.fromJson(databaseValue) ?: throw Exception("Unable to parse JSON data")
  override fun encode(value: Camera): String = adapter.toJson(value) ?: ""
}