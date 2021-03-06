package ie.otormaigh.playground.persistence.columnadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.ColumnAdapter
import ie.otormaigh.playground.Rover

class RoverColumnAdapter : ColumnAdapter<Rover, String> {
  private val adapter: JsonAdapter<Rover> by lazy { RoverJsonAdapter() }

  override fun decode(databaseValue: String): Rover = adapter.fromJson(databaseValue) ?: throw Exception("Unable to parse JSON data")
  override fun encode(value: Rover): String = adapter.toJson(value) ?: ""
}