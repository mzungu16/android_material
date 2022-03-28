package com.gleb.android_material.viewmodel

import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gleb.android_material.R
import com.gleb.android_material.database.NoteTableDAO
import com.gleb.android_material.model.DiffCallBack
import com.gleb.android_material.model.ItemTouchHelperAdapter
import com.gleb.android_material.model.Note

class NoteAdapter(
    private var mainOnNoteListener: OnNoteListener,
    private val dao: NoteTableDAO?,
    private val viewModel: NoteViewModel
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), ItemTouchHelperAdapter {
    private var noteList = mutableListOf<Note>()
    private lateinit var touchHelper: ItemTouchHelper

    fun setTouchHelper(touchHelper: ItemTouchHelper) {
        this.touchHelper = touchHelper
    }

    fun setNotes(noteListParam: MutableList<Note>) {
        val diffCallBack = DiffCallBack(this.noteList, noteListParam)
        DiffUtil.calculateDiff(diffCallBack).also {
            it.dispatchUpdatesTo(this)
        }
        noteList = noteListParam
        Log.d("NoteList", "$noteList")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_recyclerview_item, parent, false)
        return NoteViewHolder(view, mainOnNoteListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding(noteList[position])
    }

    override fun getItemCount() = noteList.size

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        val fromNote = noteList[fromPosition]
        noteList.remove(fromNote)
        noteList.add(toPosition, fromNote)
        Log.d("NoteList", "$noteList")
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemSwiped(position: Int) {
        viewModel.deleteNote(dao, noteList[position])
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }


    inner class NoteViewHolder(view: View, var onNoteListener: OnNoteListener) :
        RecyclerView.ViewHolder(view), View.OnTouchListener,
        GestureDetector.OnGestureListener {
        private val gestureDetector = GestureDetector(itemView.context, this)
        private val header = view.findViewById<TextView>(R.id.header)
        private val description = view.findViewById<TextView>(R.id.description)

        init {
            itemView.setOnTouchListener(this)
        }

        fun binding(note: Note) {
            header.text = note.header
            description.text = note.description
        }

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            gestureDetector.onTouchEvent(event)
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent?) {
            //Nothing
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            onNoteListener.onNoteClick(adapterPosition)
            return false
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
            touchHelper.startDrag(this)
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return false
        }

        /* override fun onClick(v: View?) {
             onNoteListener.onNoteClick(adapterPosition)
         }*/
    }

    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }

}

