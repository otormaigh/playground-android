package ie.otormaigh.playground.feature.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import ie.otormaigh.playground.Photo
import ie.otormaigh.playground.feature.photos.databinding.ListItemPhotoBinding

class PhotoListRecyclerAdapter(private val imageLoader: ImageLoader) : ListAdapter<Photo, PhotoListRecyclerAdapter.ViewHolder>(DIFF_UTIL) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  override fun getItemId(position: Int): Long = getItem(position).id

  inner class ViewHolder(private val binding: ListItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo) {
      // TODO: Dump image and list item if there is an error loading the image
      binding.imageView.load(
        data = item.img.replace("http://", "https://"),
        imageLoader = imageLoader
      )
    }
  }
}

private val DIFF_UTIL = object : DiffUtil.ItemCallback<Photo>() {
  override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
    oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
    oldItem == newItem
}