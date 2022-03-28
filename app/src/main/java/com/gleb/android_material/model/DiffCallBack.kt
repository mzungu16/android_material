package com.gleb.android_material.model

import androidx.recyclerview.widget.DiffUtil

open class DiffCallBack(
    private val oldList: List<Note>,
    private val newList: List<Note>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}