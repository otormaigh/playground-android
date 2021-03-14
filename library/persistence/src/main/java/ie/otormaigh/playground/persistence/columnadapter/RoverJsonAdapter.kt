package ie.otormaigh.playground.persistence.columnadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import ie.otormaigh.playground.Rover
import kotlin.Long
import kotlin.String

@Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION", "RedundantExplicitType", "LocalVariableName")
class RoverJsonAdapter : JsonAdapter<Rover>() {
  private val moshi = Moshi.Builder().build()

  private val options: JsonReader.Options = JsonReader.Options.of("id", "name", "landing_date", "launch_date", "status")

  private val longAdapter: JsonAdapter<Long> = moshi.adapter(Long::class.java, emptySet(), "id")
  private val stringAdapter: JsonAdapter<String> = moshi.adapter(
    String::class.java, emptySet(),
    "name"
  )

  override fun toString(): String = buildString(36) {
    append("GeneratedJsonAdapter(").append("Camera").append(')')
  }

  override fun fromJson(reader: JsonReader): Rover {
    var id: Long? = null
    var name: String? = null
    var landingDate: String? = null
    var launchDate: String? = null
    var status: String? = null
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> id = longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("id", "id", reader)
        1 -> name = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("name", "name", reader)
        2 -> landingDate = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("landing_date", "landing_date", reader)
        3 -> launchDate = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("launch_date", "launch_date", reader)
        4 -> status = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("status", "status", reader)
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    return Rover(
      id = id ?: throw Util.missingProperty("id", "id", reader),
      name = name ?: throw Util.missingProperty("name", "name", reader),
      landing_date = landingDate ?: throw Util.missingProperty("landing_date", "landing_date", reader),
      launch_date = launchDate ?: throw Util.missingProperty("launch_date", "launch_date", reader),
      status = status ?: throw Util.missingProperty("status", "status", reader)
    )
  }

  override fun toJson(writer: JsonWriter, value: Rover?) {
    if (value == null) {
      throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("id")
    longAdapter.toJson(writer, value.id)
    writer.name("name")
    stringAdapter.toJson(writer, value.name)
    writer.name("landing_date")
    stringAdapter.toJson(writer, value.landing_date)
    writer.name("launch_date")
    stringAdapter.toJson(writer, value.launch_date)
    writer.name("status")
    stringAdapter.toJson(writer, value.status)
    writer.endObject()
  }
}
