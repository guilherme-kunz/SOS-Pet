package guilhermekunz.com.br.sospet.ui.rescue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.model.Rescue
import guilhermekunz.com.br.sospet.utils.extensions.setImageFromBase64

class RescueAdapter(private val rescueList: ArrayList<Rescue>) :
    RecyclerView.Adapter<RescueAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rescue_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = rescueList[position]
        holder.breed.text = currentItem.name + " - " + currentItem.breed
        holder.date.text = currentItem.date
        holder.fur.text = currentItem.fur
        holder.gender.text = currentItem.gender
        holder.size.text = currentItem.size
        holder.image.setImageFromBase64(currentItem.image, R.drawable.ic_add_profile_image)
    }

    override fun getItemCount(): Int {
        return rescueList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val breed: TextView = itemView.findViewById(R.id.fragmentRescueType)
        val date: TextView = itemView.findViewById(R.id.fragmentRescueDate)
        val fur: TextView = itemView.findViewById(R.id.fragmentRescueFur)
        val gender: TextView = itemView.findViewById(R.id.fragmentRescueSex)
        val size: TextView = itemView.findViewById(R.id.fragmentRescueSize)
        val image: ImageView = itemView.findViewById(R.id.fragmentRescueImageView)
    }

}