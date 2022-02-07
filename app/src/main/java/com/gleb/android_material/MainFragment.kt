package com.gleb.android_material

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        viewModel.getNasaPODLiveData().observe(viewLifecycleOwner, Observer { responePOD ->
            with(view.findViewById<FirstCustomView>(R.id.customViewImage_id)) {
                load(responePOD.url) {
                    placeholder(R.drawable.ic_no_photo_vector)
                }
            }
            setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
            with(view.findViewById<TextView>(R.id.bottom_sheet_description_header)) {
                text = responePOD.title
            }
            with(view.findViewById<TextView>(R.id.bottom_sheet_description)) {
                text = responePOD.explanation
            }
        })
        viewModel.setNasaPODLiveDataValueMethod()
        viewModel.getNasaPODInternetAccess()


    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

}