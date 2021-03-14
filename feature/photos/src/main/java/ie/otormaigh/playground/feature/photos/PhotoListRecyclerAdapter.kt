package ie.otormaigh.playground.feature.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ie.otormaigh.playground.Photo
import ie.otormaigh.playground.feature.photos.databinding.ListItemPhotoBinding

class PhotoListRecyclerAdapter : ListAdapter<Photo, PhotoListRecyclerAdapter.ViewHolder>(DIFF_UTIL) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context)).root)

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ListItemPhotoBinding

    fun bind(item: Photo) {
//      binding.imageView.image = item.img
    }
  }
}

private val DIFF_UTIL = object : DiffUtil.ItemCallback<Photo>() {
  override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
    oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
    oldItem == newItem
}