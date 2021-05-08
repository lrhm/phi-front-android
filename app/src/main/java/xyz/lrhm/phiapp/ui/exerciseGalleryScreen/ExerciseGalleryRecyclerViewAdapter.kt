package xyz.lrhm.phiapp.ui.exerciseGalleryScreen

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import timber.log.Timber
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.MobileNavigationDirections
import xyz.lrhm.phiapp.databinding.ExerciseItemBinding


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ExerciseGalleryRecyclerViewAdapter(
    private val values: List<APIQuery.Exercise?>,
    private val parent: Fragment
) : RecyclerView.Adapter<ExerciseGalleryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val holder =  ViewHolder(
            ExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

      val width =  parent.context.resources.displayMetrics.widthPixels * 0.5
        holder.binding.imageView.layoutParams.width = width.toInt()
        holder.binding.imageView.layoutParams.height = width.toInt()

        Timber.d("width is ${holder.binding.imageView.width}, ${holder.binding.imageView.measuredWidth}")

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]!!


        holder.binding.textView.text = item.title


        val url = item!!.pictures[0]!!.url.replace("localhost", "192.168.2.5")
        Glide.with(holder.binding.imageView).load(url).into(holder.binding.imageView)

        holder.binding.button.setOnClickListener {
            val direction=  MobileNavigationDirections.actionGlobalExerciseFragment(item.id, null)
            parent.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root)



}