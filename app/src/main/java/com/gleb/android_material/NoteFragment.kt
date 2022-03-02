package com.gleb.android_material

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.gleb.android_material.database.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class NoteFragment : Fragment() {
    private lateinit var viewModel: NoteViewModel
    private val noteAdapter = NoteAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fabButton = view.findViewById<FloatingActionButton>(R.id.fab_id)
        val db = activity?.let {
            Room.databaseBuilder(
                it.applicationContext,
                AppDatabase::class.java, "database-name"
            )
                .allowMainThreadQueries()
                .build()
        }
        val noteTableDao = db?.noteDao()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_id)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        viewModel.getNoteLiveData().observe(viewLifecycleOwner) {
            noteAdapter.setNotes(it)
        }
        viewModel.setNoteLiveDataValueMethod()
        viewModel.getNoteAccess(noteTableDao)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = noteAdapter
        }

        fabButton.setOnClickListener {
            val customView = layoutInflater.inflate(R.layout.dialog_fragment, null)
            AlertDialog.Builder(context)
                .setTitle("New Note")
                .setView(customView)
                .setCancelable(false)
                .setPositiveButton("Confirm", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val headerText = customView.findViewById<TextInputEditText>(R.id.editHeader)
                        val descriptionText =
                            customView.findViewById<TextInputEditText>(R.id.editDescription)
                        val note = Note(
                            headerText.text.toString(),
                            descriptionText.text.toString()
                        )
                        viewModel.insertNote(noteTableDao, note)
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.container_layout, NoteFragment())?.commit()
                    }
                })
                .setNeutralButton("Cancel", null)
                .show()
        }
    }
}