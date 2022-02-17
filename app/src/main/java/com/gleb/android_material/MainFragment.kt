package com.gleb.android_material

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
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
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val listOfTabs: MutableList<Int> = mutableListOf()
        for (i in 0..tabLayout.childCount) {
            listOfTabs.add(i)
        }
        view.findViewById<TabLayout>(R.id.tabLayout)
            .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    //Nothing to do
                    when (tab?.position) {
                        0 -> {
                            viewModel.getNasaPODInternetAccess(currentDate)
                        }
                        1 -> {
                            val cal = Calendar.getInstance()
                            cal.add(Calendar.DATE, -1)
                            val yesterday = sdf.format(cal.time)
                            viewModel.getNasaPODInternetAccess(yesterday)
                        }
                        2 -> {
                            val cal = Calendar.getInstance()
                            cal.add(Calendar.DATE, -2)
                            val yesterdayY = sdf.format(cal.time)
                            viewModel.getNasaPODInternetAccess(yesterdayY)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    //Nothing to do
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    //Nothing to do

                }
            })

    }


    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

}