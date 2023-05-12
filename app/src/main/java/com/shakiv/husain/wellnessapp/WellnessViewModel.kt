package com.shakiv.husain.wellnessapp

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.shakiv.husain.wellnessapp.WellnessData.getWellnessTasks

class WellnessViewModel : ViewModel() {

    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: SnapshotStateList<WellnessTask>
        get() = _tasks

    fun remove(item: WellnessTask) {
        _tasks.remove(item)
    }


    fun changeTaskChecked(item: WellnessTask, checked: Boolean) {
        tasks.find { it.id==item.id }?.let {task->
            task.checked=checked
        }
    }

}