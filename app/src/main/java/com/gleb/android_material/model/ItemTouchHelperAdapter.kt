package com.gleb.android_material.model

interface ItemTouchHelperAdapter {
    fun onItemMoved(fromPosition: Int, toPosition: Int)
    fun onItemSwiped(position: Int)
}