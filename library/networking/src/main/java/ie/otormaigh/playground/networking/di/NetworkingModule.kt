package ie.otormaigh.playground.networking.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.otormaigh.playground.networking.NasaApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
  @Provides
  @Singleton
  fun provideMoshi(): Moshi =
    Moshi.Builder()
      .build()

  @Provides
  fun provideOkHttp(): OkHttpClient =
    OkHttpClient.Builder()
      .build()

  @Provides
  fun provideRetrofit(baseUrl: HttpUrl, okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
    Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()

  @Singleton
  @Provides
  fun provideNasaApi(retrofit: Retrofit): NasaApi =
    retrofit.create(NasaApi::class.java)
}