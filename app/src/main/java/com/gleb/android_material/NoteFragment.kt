package com.gleb.android_material

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.gleb.android_material.database.AppDatabase


class NoteFragment : Fragment() {
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        val db = activity?.let {
            Room.databaseBuilder(
                it.applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()
        }
        val noteTableDao = db?.noteDao()
        viewModel.getNoteLiveData().observe(viewLifecycleOwner) {

        }
    }

}