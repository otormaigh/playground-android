package ie.otormaigh.playground.networking

import com.google.common.truth.Truth.assertThat
import ie.otormaigh.playground.networking.di.NetworkingModule.provideMoshi
import ie.otormaigh.playground.networking.di.NetworkingModule.provideNasaApi
import ie.otormaigh.playground.networking.di.NetworkingModule.provideOkHttp
import ie.otormaigh.playground.networking.di.NetworkingModule.provideRetrofit
import ie.otormaigh.playground.networking.response.MOCK_JSON
import ie.otormaigh.playground.networking.response.MOCK_OBJECT
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.HttpException
import kotlin.test.assertFailsWith

class NasaApiTest {
  private val mockWebServer = MockWebServer()
  private val nasaApi = provideNasaApi(
    provideRetrofit(
      baseUrl = mockWebServer.url("/"),
      okHttpClient = provideOkHttp(),
      moshi = provideMoshi()
    )
  )

  @Test
  fun testGetPhotos_200_Success() = runBlocking {
    mockWebServer.enqueue(
      MockResponse()
        .setResponseCode(200)
        .setBody(MOCK_JSON)
    )

    val response = nasaApi.getPhotos(
      "Curiosity",
      1000,
      "2015-05-30",
      "FHAZ",
      1,
      "API_KEY"
    )

    assertThat(response)
      .isEqualTo(MOCK_OBJECT)
  }

  @Test
  fun testGetPhotos_404_Not_Found() = runBlocking {
    mockWebServer.enqueue(
      MockResponse()
        .setResponseCode(404)
    )

    val exception = assertFailsWith<HttpException> {
      nasaApi.getPhotos(
        "Curiosity",
        1000,
        "2015-05-30",
        "FHAZ",
        1,
        "API_KEY"
      )
    }

    assertThat(exception.code())
      .isEqualTo(404)
  }
}