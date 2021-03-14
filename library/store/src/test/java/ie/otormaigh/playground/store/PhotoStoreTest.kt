package ie.otormaigh.playground.store

import com.google.common.truth.Truth.assertThat
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import ie.otormaigh.playground.Photo
import ie.otormaigh.playground.networking.di.NetworkingModule
import ie.otormaigh.playground.persistence.columnadapter.CameraColumnAdapter
import ie.otormaigh.playground.persistence.columnadapter.RoverColumnAdapter
import ie.otormaigh.playground.sqldelight.Database
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PhotoStoreTest {
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
  fun testFetchPhotos(): Unit = runBlocking {
    val photoStore = PhotoStore(
      api = mockk(),
      database = database
    )

    // TODO : Mock API response to validate that function and database returns a value
    val photos = photoStore.fetchPhotos("curiosity", sol = 1).toList()
    assertThat(photos)
      .isEmpty()

    assertThat(database.photoQueries.selectAll().executeAsList())
      .isEmpty()
  }
}