package com.gleb.android_material

import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainFragment : Fragment() {
    private lateinit var viewModel: MainFragmentViewModel
    private var isExpanded = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<FirstCustomView>(R.id.customViewImage_id)
        val videoView = view.findViewById<SecondCustomView>(R.id.customViewVideo_id)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val header = view.findViewById<TextView>(R.id.bottom_sheet_description_header)
        val description = view.findViewById<TextView>(R.id.bottom_sheet_description)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())

        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.apply {
            getNasaPODLiveData().observe(viewLifecycleOwner, Observer { responePOD ->
                if (responePOD.url != null && responePOD.url.contains("youtube", true)) {
                    with(videoView) {
                        visibility = View.VISIBLE
                        imageView.visibility = View.GONE
                        val mediaController = MediaController(context).also {
                            it.setAnchorView(view.findViewById(R.id.customViewVideo_id))
                            it.setMediaPlayer(view.findViewById(R.id.customViewVideo_id))
                        }
                        setMediaController(mediaController)
                        setVideoURI(Uri.parse(responePOD.url))
                        start()
                    }
                } else {
                    imageView.load(responePOD.url) { placeholder(R.drawable.ic_no_photo_vector) }
                    imageView.setOnClickListener {
                        isExpanded = !isExpanded
                        TransitionManager.beginDelayedTransition(
                            view.findViewById(R.id.frame_image_animation), TransitionSet()
                                .addTransition(ChangeBounds())
                                .addTransition(ChangeImageTransform())
                        )

                        val params = it.layoutParams.also { params ->
                            params.height = if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT
                            else ViewGroup.LayoutParams.WRAP_CONTENT
                        }
                        it.layoutParams = params

                        imageView.scaleType =
                            if (isExpanded) ImageView.ScaleType.CENTER_CROP
                            else ImageView.ScaleType.FIT_CENTER
                    }
                }
                TransitionManager.beginDelayedTransition(view.findViewById(R.id.text_container))
                header.text = responePOD.title
                description.text = responePOD.explanation
            })
            setNasaPODLiveDataValueMethod()
            getNasaPODInternetAccess(currentDate)
        }

        val listOfTabs: MutableList<Int> = mutableListOf()
        for (i in 0..tabLayout.childCount) {
            listOfTabs.add(i)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.getNasaPODInternetAccess(currentDate)
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
}