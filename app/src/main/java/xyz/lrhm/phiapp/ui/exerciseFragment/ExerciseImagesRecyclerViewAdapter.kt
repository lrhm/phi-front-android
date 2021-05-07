package xyz.lrhm.phiapp.ui.exerciseFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.lrhm.GetUserQuery
import xyz.lrhm.phiapp.databinding.ExerciseItemBinding
import xyz.lrhm.phiapp.databinding.ImageCarouselItemBinding
import xyz.lrhm.phiapp.ui.exerciseGalleryScreen.ExerciseGalleryRecyclerViewAdapter

class ExerciseImagesRecyclerViewAdapter(private val images: List<GetUserQuery.Picture>):
    RecyclerView.Adapter<ExerciseImagesRecyclerViewAdapter.ViewHolder>(){


    inner class ViewHolder(val binding: ImageCarouselItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ImageCarouselItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = images[position]!!

        val url = item.url.replace("localhost", "192.168.2.5")
        Glide.with(holder.binding.imageView).load(url).into(holder.binding.imageView)

    }

    override fun getItemCount(): Int = images.size

}