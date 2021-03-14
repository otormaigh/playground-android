package ie.otormaigh.playground.persistence.columnadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.ColumnAdapter
import ie.otormaigh.playground.Rover

//FIXME: Add actual parsers here.
class RoverColumnAdapter(moshi: Moshi) : ColumnAdapter<Rover, String> {
  private val adapter: JsonAdapter<Rover> by lazy { moshi.adapter(Rover::class.java) }

  override fun decode(databaseValue: String): Rover = adapter.fromJson(databaseValue) ?: throw Exception("Unable to parse JSON data")
  override fun encode(value: Rover): String = adapter.toJson(value) ?: ""
}