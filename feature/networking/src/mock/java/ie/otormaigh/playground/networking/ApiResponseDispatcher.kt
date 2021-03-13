package ie.otormaigh.playground.networking

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import okhttp3.mockwebserver.RecordedRequest
import timber.log.Timber
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit

class ApiResponseDispatcher : QueueDispatcher() {
  override fun dispatch(request: RecordedRequest): MockResponse {
    Timber.d("request.path -> ${request.path}")

    return MockResponse().apply {
      val method = request.method ?: ""
      val path = request.path ?: ""
      Timber.d("method -> $method")
      Timber.d("path -> $path")

      // Set a default client side error to begin with
      setResponseCode(404)
      setBody("No method found for path [$method:$path]")

      if (path.contains("/photos")) {
        setBody(readFile("get_mars_photos.json"))
        setResponseCode(200)
      }

      setBodyDelay(1_300, TimeUnit.MILLISECONDS)
    }
  }

  private fun readFile(fileName: String): String {
    return ApiResponseDispatcher::class.java.getResourceAsStream("/$fileName")?.use {
      val size = it.available()
      val buffer = ByteArray(size)
      it.read(buffer)
      it.close()

      String(buffer, StandardCharsets.UTF_8)
    } ?: ""
  }

  private fun MockResponse.parseRequest(request: RecordedRequest) {
    val requestedMethod = request.method?.toLowerCase(Locale.getDefault())
    val fileName = requestedMethod + request.path
      ?.replace("/", "_")
      ?.replaceAfter("?", "")
      ?.dropLast(2) + ".json"
    Timber.d("fileName -> $fileName")

    setBody(readFile(fileName))
    setResponseCode(200)
  }
}