package edu.rosehulman.kaupaies.carcompanion.ui.car_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.kaupaies.carcompanion.MainActivity
import edu.rosehulman.kaupaies.carcompanion.R

class CarDetailFragment(var main: MainActivity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_car_details, container, false) as RecyclerView
        view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        var adapter = CarDetailAdapter(context, main)
        view.adapter = adapter
        view.layoutManager = LinearLayoutManager(context)
        view.setHasFixedSize(true)
        return view
    }

}
