package com.example.draganddrop_compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.burnoutcrew.reorderable.ItemPosition

class DragAndDropViewModel : ViewModel() {

    var dogs by mutableStateOf(List(5) { ItemData("${it+1}", "id$it",it == 2 )})


    fun moveDog(from: ItemPosition, to: ItemPosition) {
        dogs = dogs.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }

    fun isDogDragEnabled(draggedOver: ItemPosition, dragging: ItemPosition) =
        dogs.getOrNull(draggedOver.index)?.isLocked != true &&  dogs.getOrNull(dragging.index)?.isLocked != true

}


data class ItemData(val text: String, val key: String, val isLocked: Boolean = false)