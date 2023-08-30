package guilhermekunz.com.br.sospet.ui.wanted

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.model.Wanted
import guilhermekunz.com.br.sospet.utils.extensions.setImageFromBase64

class WantedAdapter(private val wantedList: ArrayList<Wanted>) :
    RecyclerView.Adapter<WantedAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.wanted_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = wantedList[position]
        holder.breed.text = currentItem.name + " - " + currentItem.breed
        holder.date.text = currentItem.date
        holder.fur.text = currentItem.fur
        holder.gender.text = currentItem.gender
        holder.size.text = currentItem.size
        holder.image.setImageFromBase64(currentItem.image, R.drawable.ic_add_profile_image)
    }

    override fun getItemCount(): Int {
        return wantedList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val breed: TextView = itemView.findViewById(R.id.fragmentWantedType)
        val date: TextView = itemView.findViewById(R.id.fragmentWantedDate)
        val fur: TextView = itemView.findViewById(R.id.fragmentWantedFur)
        val gender: TextView = itemView.findViewById(R.id.fragmentWantedSex)
        val size: TextView = itemView.findViewById(R.id.fragmentWantedSize)
        val image: ImageView = itemView.findViewById(R.id.fragmentWantedImageView)
    }

}