import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.database.models.CarObject
import com.example.carcompanion.databinding.CarListItemBinding

class CarListAdapter(private val carList: List<CarObject>) :
    RecyclerView.Adapter<CarListAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = CarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val carObject = carList[position]
        holder.bind(carObject)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    inner class CarViewHolder(private val binding: CarListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(carObject: CarObject) {
            binding.nickname.text = carObject.nickname
            binding.yearMakeModel.text = "${carObject.year} ${carObject.make} ${carObject.model}"
        }
    }
}
