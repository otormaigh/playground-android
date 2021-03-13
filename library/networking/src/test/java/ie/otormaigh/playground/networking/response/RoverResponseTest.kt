package ie.otormaigh.playground.networking.response

import com.google.common.truth.Truth.assertThat
import ie.otormaigh.playground.networking.di.NetworkingModule
import org.junit.Test

class RoverResponseTest {
  private val adapter = NetworkingModule.provideMoshi().adapter(RoverResponse::class.java)

  @Test
  fun testToJson() {
    val response = RoverResponse(
      id = 5,
      name = "Curiosity",
      landingDate = "2012-08-06",
      launchDate = "2011-11-26",
      status = "active"
    )

    assertThat(adapter.toJson(response))
      .isEqualTo("""{"id":5,"name":"Curiosity","landing_date":"2012-08-06","launch_date":"2011-11-26","status":"active"}""")
  }

  @Test
  fun testFromJson() {
    val json = """{"id":5,"name":"Curiosity","landing_date":"2012-08-06","launch_date":"2011-11-26","status":"active"}"""

    assertThat(adapter.fromJson(json))
      .isEqualTo(
        RoverResponse(
          id = 5,
          name = "Curiosity",
          landingDate = "2012-08-06",
          launchDate = "2011-11-26",
          status = "active"
        )
      )
  }
}