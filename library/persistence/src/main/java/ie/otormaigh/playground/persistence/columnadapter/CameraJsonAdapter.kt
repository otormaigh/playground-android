package ie.otormaigh.playground.persistence.columnadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import ie.otormaigh.playground.Camera
import kotlin.Long
import kotlin.String

@Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION", "RedundantExplicitType", "LocalVariableName")
class CameraJsonAdapter : JsonAdapter<Camera>() {
  private val moshi = Moshi.Builder().build()

  private val options: JsonReader.Options = JsonReader.Options.of("id", "name", "rover_id", "full_name")

  private val longAdapter: JsonAdapter<Long> = moshi.adapter(Long::class.java, emptySet(), "id")
  private val stringAdapter: JsonAdapter<String> = moshi.adapter(
    String::class.java, emptySet(),
    "name"
  )

  override fun toString(): String = buildString(36) {
    append("GeneratedJsonAdapter(").append("Camera").append(')')
  }

  override fun fromJson(reader: JsonReader): Camera {
    var id: Long? = null
    var name: String? = null
    var roverId: Long? = null
    var fullName: String? = null
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> id = longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("id", "id", reader)
        1 -> name = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("name", "name", reader)
        2 -> roverId = longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("rover_id", "rover_id", reader)
        3 -> fullName = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("full_name", "full_name", reader)
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    return Camera(
      id = id ?: throw Util.missingProperty("id", "id", reader),
      name = name ?: throw Util.missingProperty("name", "name", reader),
      rover_id = roverId ?: throw Util.missingProperty("rover_id", "rover_id", reader),
      full_name = fullName ?: throw Util.missingProperty("full_name", "full_name", reader)
    )
  }

  override fun toJson(writer: JsonWriter, value: Camera?) {
    if (value == null) {
      throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("id")
    longAdapter.toJson(writer, value.id)
    writer.name("name")
    stringAdapter.toJson(writer, value.name)
    writer.name("rover_id")
    longAdapter.toJson(writer, value.rover_id)
    writer.name("full_name")
    stringAdapter.toJson(writer, value.full_name)
    writer.endObject()
  }
}
