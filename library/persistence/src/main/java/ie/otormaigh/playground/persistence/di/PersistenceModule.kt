package ie.otormaigh.playground.persistence.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.otormaigh.playground.Photo
import ie.otormaigh.playground.persistence.columnadapter.CameraColumnAdapter
import ie.otormaigh.playground.persistence.columnadapter.RoverColumnAdapter
import ie.otormaigh.playground.sqldelight.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
  @Provides
  fun provideSqlDriver(application: Application): SqlDriver =
    AndroidSqliteDriver(
      schema = Database.Schema,
      context = application,
      name = "playground.db"
    )

  @Provides
  @Singleton
  fun provideDatabase(sqlDriver: SqlDriver, moshi: Moshi): Database = Database(
    driver = sqlDriver,
    PhotoAdapter = Photo.Adapter(
      cameraAdapter = CameraColumnAdapter(moshi),
      roverAdapter = RoverColumnAdapter(moshi)
    )
  )
}