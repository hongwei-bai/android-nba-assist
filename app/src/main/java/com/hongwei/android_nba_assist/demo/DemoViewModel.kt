package com.hongwei.android_nba_assist.demo

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor() : ViewModel() {
    val color = MutableLiveData<Color>()

    val colorList = listOf(
        Color.Black, Color.Blue, Color.Red, Color.Cyan, Color.Green, Color.Magenta, Color.Yellow, Color.White
    )

    init {
        color.value = Color.Black
    }

    var index = 0

    fun altColor() {
        index++
        index %= colorList.size
        color.value = colorList[index]
    }
}