package ie.otormaigh.playground.persistence.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
  fun provideDatabase(sqlDriver: SqlDriver): Database = Database(
    driver = sqlDriver
  )
}