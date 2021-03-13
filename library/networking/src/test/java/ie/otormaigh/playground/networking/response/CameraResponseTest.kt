package ie.otormaigh.playground.networking.response

import com.google.common.truth.Truth.assertThat
import ie.otormaigh.playground.networking.di.NetworkingModule
import org.junit.Test

class CameraResponseTest {
  private val adapter = NetworkingModule.provideMoshi().adapter(CameraResponse::class.java)

  @Test
  fun testToJson() {
    val response = CameraResponse(
      id = 20,
      name = "FHAZ",
      roverId = 5,
      fullName = "Front Hazard Avoidance Camera"
    )

    assertThat(adapter.toJson(response))
      .isEqualTo("""{"id":20,"name":"FHAZ","rover_id":5,"full_name":"Front Hazard Avoidance Camera"}""")
  }

  @Test
  fun testFromJson() {
    val json = """{"id":20,"name":"FHAZ","rover_id":5,"full_name":"Front Hazard Avoidance Camera"}"""

    assertThat(adapter.fromJson(json))
      .isEqualTo(
        CameraResponse(
          id = 20,
          name = "FHAZ",
          roverId = 5,
          fullName = "Front Hazard Avoidance Camera"
        )
      )
  }
}