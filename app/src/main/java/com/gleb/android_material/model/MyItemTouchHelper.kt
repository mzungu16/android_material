package com.gleb.android_material.model

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gleb.android_material.R

class MyItemTouchHelper(private val itemAdapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.setBackgroundColor(
            ContextCompat.getColor(viewHolder.itemView.context, R.color.white)
        )
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_DRAG -> viewHolder?.itemView?.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val moveFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags,moveFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        itemAdapter.onItemMoved(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemAdapter.onItemSwiped(viewHolder.adapterPosition)
    }
}