package ie.otormaigh.playground.networking.response

import com.google.common.truth.Truth.assertThat
import ie.otormaigh.playground.networking.di.NetworkingModule
import org.junit.Test

class PhotoResponseTest {
  private val adapter = NetworkingModule.provideMoshi().adapter(PhotoResponse::class.java)

  @Test
  fun testToJson() {
    val response = PhotoResponse(
      id = 102693,
      sol = 1000,
      camera = CameraResponse(
        id = 20,
        name = "FHAZ",
        roverId = 5,
        fullName = "Front Hazard Avoidance Camera"
      ),
      img = "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG",
      earthDate = "2015-05-30",
      rover = RoverResponse(
        id = 5,
        name = "Curiosity",
        landingDate = "2012-08-06",
        launchDate = "2011-11-26",
        status = "active"
      )
    )

    assertThat(adapter.toJson(response))
      .isEqualTo("""{"id":102693,"sol":1000,"camera":{"id":20,"name":"FHAZ","rover_id":5,"full_name":"Front Hazard Avoidance Camera"},"img_src":"http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG","earth_date":"2015-05-30","rover":{"id":5,"name":"Curiosity","landing_date":"2012-08-06","launch_date":"2011-11-26","status":"active"}}""")
  }

  @Test
  fun testFromJson() {
    val json =
      """{"id":102693,"sol":1000,"camera":{"id":20,"name":"FHAZ","rover_id":5,"full_name":"Front Hazard Avoidance Camera"},"img_src":"http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG","earth_date":"2015-05-30","rover":{"id":5,"name":"Curiosity","landing_date":"2012-08-06","launch_date":"2011-11-26","status":"active"}}"""

    assertThat(adapter.fromJson(json))
      .isEqualTo(
        PhotoResponse(
          id = 102693,
          sol = 1000,
          camera = CameraResponse(
            id = 20,
            name = "FHAZ",
            roverId = 5,
            fullName = "Front Hazard Avoidance Camera"
          ),
          img = "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG",
          earthDate = "2015-05-30",
          rover = RoverResponse(
            id = 5,
            name = "Curiosity",
            landingDate = "2012-08-06",
            launchDate = "2011-11-26",
            status = "active"
          )
        )
      )
  }
}