package com.gleb.android_material

import android.content.Intent
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

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
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.apply {
            getNasaPODLiveData().observe(viewLifecycleOwner, Observer { responePOD ->
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
            setNasaPODLiveDataValueMethod()
            getNasaPODInternetAccess(currentDate)
        }
        val editText = view.findViewById<TextInputEditText>(R.id.input_edit_text)
        view.findViewById<TextInputLayout>(R.id.input_layout).setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${editText.text.toString()}")
            })
        }

        view.findViewById<ChipGroup>(R.id.chip_group).setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chip_1 -> {
                    val cal = Calendar.getInstance()
                    cal.add(Calendar.DATE, -2)
                    val yesterdayY = sdf.format(cal.time)
                    viewModel.getNasaPODInternetAccess(yesterdayY)
                }
                R.id.chip_2 -> {
                    val cal = Calendar.getInstance()
                    cal.add(Calendar.DATE, -1)
                    val yesterday = sdf.format(cal.time)
                    viewModel.getNasaPODInternetAccess(yesterday)
                }
                R.id.chip_3 -> viewModel.getNasaPODInternetAccess(currentDate)
                else -> viewModel.getNasaPODInternetAccess(currentDate)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

}