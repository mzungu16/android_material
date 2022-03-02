package com.gleb.android_material

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var noteList = listOf<Note>()


    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header = view.findViewById<TextView>(R.id.header)
        val description = view.findViewById<TextView>(R.id.description)
        fun binding(note: Note) {
            header.text = note.header
            description.text = note.description
        }
    }

    fun setNotes(noteListParam: List<Note>) {
        val diffCallBack = DiffCallBack(this.noteList, noteListParam)
        DiffUtil.calculateDiff(diffCallBack).also {
            it.dispatchUpdatesTo(this)
        }
        noteList = noteListParam
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_recyclerview_item, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding(noteList[position])
    }

    override fun getItemCount() = noteList.size
}