package ie.otormaigh.playground.feature.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import ie.otormaigh.playground.Camera
import ie.otormaigh.playground.Photo
import ie.otormaigh.playground.Rover
import ie.otormaigh.playground.store.PhotoStore
import kotlinx.coroutines.Dispatchers

@ExperimentalFoundationApi
@Composable
fun PhotoListView(photoStore: PhotoStore) {
  LazyVerticalGrid(cells = GridCells.Fixed(2)) {
    item {
      PhotoListItem(
        photo = Photo(
          id = 102693,
          sol = 1000,
          camera = Camera(
            id = 20,
            name = "FHAZ",
            rover_id = 5,
            full_name = "Front Hazard Avoidance Camera",
          ),
          img = "https://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG",
          earth_date = "2015-05-30",
          rover = Rover(
            id = 5,
            name = "Curiosity",
            landing_date = "2012-08-06",
            launch_date = "2011-11-26",
            status = "active"
          )
        )
      )
    }
    item {
      PhotoListItem(
        photo = Photo(
          id = 102693,
          sol = 1000,
          camera = Camera(
            id = 20,
            name = "FHAZ",
            rover_id = 5,
            full_name = "Front Hazard Avoidance Camera",
          ),
          img = "https://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG",
          earth_date = "2015-05-30",
          rover = Rover(
            id = 5,
            name = "Curiosity",
            landing_date = "2012-08-06",
            launch_date = "2011-11-26",
            status = "active"
          )
        )
      )
    }
    item {
      PhotoListItem(
        photo = Photo(
          id = 102693,
          sol = 1000,
          camera = Camera(
            id = 20,
            name = "FHAZ",
            rover_id = 5,
            full_name = "Front Hazard Avoidance Camera",
          ),
          img = "https://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG",
          earth_date = "2015-05-30",
          rover = Rover(
            id = 5,
            name = "Curiosity",
            landing_date = "2012-08-06",
            launch_date = "2011-11-26",
            status = "active"
          )
        )
      )
    }
  }
}

//private fun fetchPhotos(photoStore: PhotoStore) {
//  rememberCoroutineScope().launch(Dispatchers.IO) {
//    photoStore.fetchPhotos("curiosity", sol = 1000).collectLatest { photos ->
//      withContext(Dispatchers.Main) {
//        photos.forEach { photo ->
//
//        }
//      }
//    }
//  }
//}

@Preview
@Composable
fun PhotoListViewPreview() {
}