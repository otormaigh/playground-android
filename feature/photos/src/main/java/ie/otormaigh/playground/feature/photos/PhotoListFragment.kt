package ie.otormaigh.playground.feature.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ie.otormaigh.playground.feature.photos.databinding.FragmentPhotoListBinding
import ie.otormaigh.playground.store.PhotoStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PhotoListFragment : Fragment() {
  @Inject
  lateinit var photoStore: PhotoStore

  private lateinit var binding: FragmentPhotoListBinding
  private val recyclerAdapter by lazy { PhotoListRecyclerAdapter() }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentPhotoListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.recyclerView.adapter = recyclerAdapter
    binding.recyclerView.layoutManager = GridLayoutManager(context, 2)

    lifecycleScope.launch(Dispatchers.IO) {
      photoStore.fetchPhotos("curiosity", sol = 1000).collectLatest {
        withContext(Dispatchers.Main) {
          recyclerAdapter.submitList(it)
        }
      }
    }
  }
}