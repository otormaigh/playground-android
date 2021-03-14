package ie.otormaigh.playground

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PlaygroundApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.BUILD_TYPE != "release") {
      Timber.plant(object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement) = "(${element.fileName}:${element.lineNumber})"
      })
    }
  }
}