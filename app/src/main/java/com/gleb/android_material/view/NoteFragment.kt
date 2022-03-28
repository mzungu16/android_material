package com.gleb.android_material.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.gleb.android_material.R
import com.gleb.android_material.database.AppDatabase
import com.gleb.android_material.model.MyItemTouchHelper
import com.gleb.android_material.model.Note
import com.gleb.android_material.viewmodel.NoteAdapter
import com.gleb.android_material.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText


class NoteFragment : Fragment(), NoteAdapter.OnNoteListener {
    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
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
        val list = mutableListOf<Note>()
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        viewModel.getNoteLiveData().observe(viewLifecycleOwner) {
            for (i in it) {
                list.add(i)
            }
            noteAdapter.setNotes(list)
        }
        viewModel.setNoteLiveDataValueMethod()
        viewModel.getNoteAccess(noteTableDao)
        noteAdapter = NoteAdapter(this, noteTableDao, viewModel)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val callBack = MyItemTouchHelper(noteAdapter)
            val itemTouchHelper = ItemTouchHelper(callBack)
            noteAdapter.setTouchHelper(itemTouchHelper)
            itemTouchHelper.attachToRecyclerView(this)
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
                            (0..50).random(),
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

    override fun onNoteClick(position: Int) {
        Log.d("TAG", "Note clicked $position")
    }

}