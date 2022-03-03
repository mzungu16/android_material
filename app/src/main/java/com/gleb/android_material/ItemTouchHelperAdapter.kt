package com.gleb.android_material

interface ItemTouchHelperAdapter {
    fun onItemMoved(fromPosition: Int, toPosition: Int)
    fun onItemSwiped(position: Int)
}