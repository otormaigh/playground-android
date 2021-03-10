package ie.otormaigh.playground.networking.response

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

    val json = adapter.toJson(response)

  }
}