import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.AbsListView
import com.example.popview.R
class AvatarAdapter(
    private val context: Context,
    private val avatars: List<Int>,
    private val onAvatarSelected: (Int) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = avatars.size
    override fun getItem(position: Int): Int = avatars[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView = convertView as? ImageView ?: ImageView(context).apply {
            layoutParams = AbsListView.LayoutParams(200, 200) // Tamaño de las imágenes
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        imageView.setImageResource(avatars[position])
        imageView.setOnClickListener {
            onAvatarSelected(avatars[position])
        }
        return imageView
    }
}