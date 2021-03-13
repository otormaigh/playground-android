package ie.otormaigh.playground.networking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkFlavourModule {
  @Provides
  fun provideBaseUrl(): HttpUrl =
    "".toHttpUrl()
}