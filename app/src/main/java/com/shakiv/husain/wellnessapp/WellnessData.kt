package com.shakiv.husain.wellnessapp

object WellnessData {

    fun getWellnessTasks() = List(30){index: Int ->
    WellnessTask(index,"Task # $index")
    }
}