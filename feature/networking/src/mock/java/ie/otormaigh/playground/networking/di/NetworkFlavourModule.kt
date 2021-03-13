package ie.otormaigh.playground.networking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.otormaigh.playground.networking.ApiResponseDispatcher
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer

@Module
@InstallIn(SingletonComponent::class)
object NetworkFlavourModule {
  @Provides
  fun provideBaseUrl(): HttpUrl =
    MockWebServer().apply {
      dispatcher = ApiResponseDispatcher()
    }.url("/")
}