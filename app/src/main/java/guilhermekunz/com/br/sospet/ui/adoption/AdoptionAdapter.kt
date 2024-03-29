package guilhermekunz.com.br.sospet.ui.adoption

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.model.Adoption
import guilhermekunz.com.br.sospet.utils.extensions.setImageFromBase64

class AdoptionAdapter(private val adoptionList: ArrayList<Adoption>) :
    RecyclerView.Adapter<AdoptionAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.adoption_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return adoptionList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val breed: TextView = itemView.findViewById(R.id.fragmentAdoptionType)
        val date: TextView = itemView.findViewById(R.id.fragmentAdoptionDate)
        val fur: TextView = itemView.findViewById(R.id.fragmentAdoptionFur)
        val gender: TextView = itemView.findViewById(R.id.fragmentAdoptionSex)
        val size: TextView = itemView.findViewById(R.id.fragmentAdoptionSize)
        val image: ImageView = itemView.findViewById(R.id.fragmentAdoptionImageView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = adoptionList[position]
        holder.breed.text = currentItem.name + " - " + currentItem.breed
        holder.date.text = currentItem.date
        holder.fur.text = currentItem.fur
        holder.gender.text = currentItem.gender
        holder.size.text = currentItem.size
        holder.image.setImageFromBase64(currentItem.image, R.drawable.ic_add_profile_image)
    }

}