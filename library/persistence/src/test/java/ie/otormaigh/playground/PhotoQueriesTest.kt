package ie.otormaigh.playground

import com.google.common.truth.Truth.assertThat
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import ie.otormaigh.playground.persistence.columnadapter.CameraColumnAdapter
import ie.otormaigh.playground.persistence.columnadapter.RoverColumnAdapter
import ie.otormaigh.playground.sqldelight.Database
import org.junit.Before
import org.junit.Test

class PhotoQueriesTest {
  lateinit var database: Database

  @Before
  fun setup() {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    Database.Schema.create(driver)
    database = Database(
      driver = driver,
      PhotoAdapter = Photo.Adapter(
        cameraAdapter = CameraColumnAdapter(),
        roverAdapter = RoverColumnAdapter()
      )
    )
  }

  @Test
  fun testInsertOrReplace() {
    val photo = Photo(
      id = 102693,
      sol = 1000,
      camera = Camera(
        id = 20,
        name = "FHAZ",
        rover_id = 5,
        full_name = "Front Hazard Avoidance Camera"
      ),
      img = "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG",
      earth_date = "2015-05-30",
      rover = Rover(
        id = 5,
        name = "Curiosity",
        landing_date = "2012-08-06",
        launch_date = "2011-11-26",
        status = "active"
      )
    )

    database.photoQueries.insertOrReplace(photo)
    assertThat(database.photoQueries.selectAll().executeAsList().size)
      .isEqualTo(1)
    assertThat(database.photoQueries.selectAll().executeAsOne().sol)
      .isEqualTo(1000)

    // Photo.sol update
    database.photoQueries.insertOrReplace(photo.copy(sol = 1001))
    assertThat(database.photoQueries.selectAll().executeAsList().size)
      .isEqualTo(1)
    assertThat(database.photoQueries.selectAll().executeAsOne().sol)
      .isEqualTo(1001)

    // Rover.name update
    database.photoQueries.insertOrReplace(photo.copy(rover = photo.rover.copy(name = "Perseverence")))
    assertThat(database.photoQueries.selectAll().executeAsList().size)
      .isEqualTo(1)
    assertThat(database.photoQueries.selectAll().executeAsOne().rover.name)
      .isEqualTo("Perseverence")
  }
}