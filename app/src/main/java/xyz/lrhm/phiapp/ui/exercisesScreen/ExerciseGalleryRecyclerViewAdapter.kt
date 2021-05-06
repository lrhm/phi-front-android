package xyz.lrhm.phiapp.ui.exercisesScreen

import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import xyz.lrhm.GetUserQuery
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.databinding.ExerciseItemBinding


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ExerciseGalleryRecyclerViewAdapter(
    private val values: List<GetUserQuery.Exercise?>
) : RecyclerView.Adapter<ExerciseGalleryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]!!

        holder.binding.textView.text = item.title


        val url = item.pictures[0]!!.url.replace("localhost", "192.168.2.5")
        Glide.with(holder.binding.imageView).load(url).into(holder.binding.imageView)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root)



}