package com.pkdev.tummoctask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pkdev.tummoctask.adapter.MapAdapter
import com.pkdev.tummoctask.databinding.FragmentMapBottomBinding


class MapBottomFragment : Fragment() {

    private lateinit var binding: FragmentMapBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
//        isCancelable = false

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.mapRv.layoutManager = LinearLayoutManager(requireContext())
        binding.mapRv.adapter = MapAdapter(arrayListOf("source","transit","destination"), requireContext(), requireActivity())


    }

}