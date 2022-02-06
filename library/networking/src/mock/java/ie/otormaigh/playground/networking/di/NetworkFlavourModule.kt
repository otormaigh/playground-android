package ie.otormaigh.playground.networking.di

import android.os.StrictMode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.otormaigh.playground.networking.ApiResponseDispatcher
import mockwebserver3.MockWebServer
import okhttp3.HttpUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkFlavourModule {
  @Provides
  fun provideBaseUrl(): HttpUrl {
    // Dagger will call this from the main thread which will cause a 'NetworkOnMainThreadException'
    // once { MockWebServer.url() } is called, so set this ThreadPolicy as soon as we can.
    StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
    return MockWebServer().apply {
      dispatcher = ApiResponseDispatcher()
    }.url("/")
  }
}