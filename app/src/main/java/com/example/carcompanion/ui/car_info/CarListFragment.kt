import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carcompanion.R
import com.example.carcompanion.databinding.FragmentCarListBinding

class CarListFragment(val user: String) : Fragment() {

    private lateinit var binding: FragmentCarListBinding
    private lateinit var carListAdapter: CarListAdapter
    private lateinit var carListViewModel: CarListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emptyCarListMessage = view.findViewById<TextView>(R.id.empty_car_list_message)
        carListViewModel = ViewModelProvider(this).get(CarListViewModel::class.java)

        carListViewModel.carListLiveData.observe(viewLifecycleOwner) { carList ->
            // Update the adapter with the fetched carList
            if (carList.isNotEmpty()) {
                carListAdapter = CarListAdapter(carList)
                binding.carListRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = carListAdapter
                }
                emptyCarListMessage.visibility = View.GONE
            } else {
                emptyCarListMessage.visibility = View.VISIBLE
            }
        }

        carListViewModel.fetchCarList(user)
    }
}
