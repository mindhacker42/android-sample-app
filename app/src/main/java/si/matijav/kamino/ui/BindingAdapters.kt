package si.matijav.kamino.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import si.matijav.kamino.R

object BindingAdapters {

    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageUrl: String?) {
        imageUrl ?: return

        Glide.with(imageView.context)
            .load(imageUrl)
            .error(R.drawable.ic_error)
            .into(imageView)
    }
}