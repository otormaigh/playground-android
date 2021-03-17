package ie.otormaigh.playground.feature.photos

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.util.DebugLogger
import dev.chrisbanes.accompanist.coil.CoilImage
import ie.otormaigh.playground.Camera
import ie.otormaigh.playground.Photo
import ie.otormaigh.playground.Rover

@Composable
fun PhotoListItem(photo: Photo) {
  CoilImage(
    data = photo.img,
    contentDescription = "Rover: ${photo.rover.name}, Camera: ${photo.camera.name} (${photo.camera.full_name}), Sol: ${photo.sol}, Earth Date: ${photo.earth_date}",
    fadeIn = true,
    imageLoader = ImageLoader.Builder(LocalContext.current)
      .logger(DebugLogger(Log.VERBOSE))
      .build()
  )
}

@Preview
@Composable
fun PhotoListItemPreview() {
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