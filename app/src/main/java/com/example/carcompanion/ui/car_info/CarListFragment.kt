import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carcompanion.databinding.FragmentCarListBinding

class CarListFragment : Fragment() {

    private lateinit var binding: FragmentCarListBinding
    private lateinit var carListAdapter: CarListAdapter
    private lateinit var carListViewModel: CarListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carListViewModel = ViewModelProvider(this).get(CarListViewModel::class.java)

        carListViewModel.carListLiveData.observe(viewLifecycleOwner) { carList ->
            // Update the adapter with the fetched carList
            carListAdapter = CarListAdapter(carList)
            binding.carListRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = carListAdapter
            }
        }

        // TODO: Change to user
        carListViewModel.fetchCarList(            "vzEIAKfgspSHePX6K32hcmclIO32"        )
    }
}
