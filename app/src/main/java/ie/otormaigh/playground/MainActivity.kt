package ie.otormaigh.playground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.otormaigh.playground.feature.photos.PhotoListView
import ie.otormaigh.playground.store.PhotoStore
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {
  @Inject
  lateinit var photoStore: PhotoStore

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val navController = rememberNavController()
      NavHost(navController, startDestination = "photos") {
        composable("photos") {
          PhotoListView(photoStore)
        }
      }
    }
  }
}