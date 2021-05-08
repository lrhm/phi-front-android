package xyz.lrhm.phiapp.ui.exerciseFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.databinding.ExerciseItemBinding
import xyz.lrhm.phiapp.databinding.ImageCarouselItemBinding
import xyz.lrhm.phiapp.ui.exerciseGalleryScreen.ExerciseGalleryRecyclerViewAdapter

class ExerciseImagesRecyclerViewAdapter(private val images: List<APIQuery.Picture?>):
    RecyclerView.Adapter<ExerciseImagesRecyclerViewAdapter.ViewHolder>(){


    inner class ViewHolder(val binding: ImageCarouselItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            ImageCarouselItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
//        val width =  parent.context.resources.displayMetrics.widthPixels * 0.8
//        holder.binding.imageView.layoutParams.width = width.toInt()
//        holder.binding.imageView.layoutParams.height = width.toInt()
        return  holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = images[position]!!

        val url = item.url.replace("localhost", "192.168.2.5")
        Glide.with(holder.binding.imageView).load(url).into(holder.binding.imageView)

    }

    override fun getItemCount(): Int = images.size

}