package com.example.carcompanion.ui.troubleshooting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carcompanion.Constants
import com.example.carcompanion.databinding.FragmentTroubleshootingBinding

class TroubleshootingFragment : Fragment() {

    private var listener: OnTroubleSelectedListener? = null

    private lateinit var binding: FragmentTroubleshootingBinding

    lateinit var recylclerParent: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTroubleshootingBinding.inflate(inflater, container, false)
        var view = binding.troubleshootingRecycler

        Log.d(Constants.TAG, "opened troubleshooter")
        val adapter = TroubleAdapter(context, listener)
        setView(view, adapter)

        setRestartButton(binding,adapter);
        setBackStepButton(binding);
        setnextStepButton(binding);

        return binding.root
    }

    private fun setView(view: RecyclerView, adapter: TroubleAdapter) {
        view.adapter = adapter
        view.layoutManager = LinearLayoutManager(context)
        view.setHasFixedSize(true)
    }

    private fun setRestartButton(binding: FragmentTroubleshootingBinding, adapter: TroubleAdapter) {
        binding.restartButton.setOnClickListener {
            Log.d(Constants.TAG, "restart button pressed")
            adapter.restartTroubleshooting()
        }
    }

    private fun setBackStepButton(binding: FragmentTroubleshootingBinding) {
        binding.backStepButton.setOnClickListener {
            Log.d(Constants.TAG, "back step button pressed")
        }
    }
    private fun setnextStepButton(binding: FragmentTroubleshootingBinding) {
        binding.nextStepButton.setOnClickListener {
            Log.d(Constants.TAG, "next step button pressed")
        }
    }
    override fun onAttach(context: Context){
        super.onAttach(context)
        Log.d(Constants.TAG, "attempting to attach troubleshoot")
        if(context is OnTroubleSelectedListener){
            listener = context
        } else {
            throw RuntimeException(context.toString() + "must implement OnTroubleSelected")
        }
    }

    override fun onDetach(){
        super.onDetach()
        listener = null
    }

    interface OnTroubleSelectedListener {
        fun onTroubleSelected(woe: TroubleShootingTree.Woe)
    }
}
