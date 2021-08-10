package com.app.mamochallenge.ui.mainScreen

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val keyboardItemsList: List<Char> by lazy {
        val list = mutableListOf<Char>()
        for (number in 1..9) {
            list.add(number.toString().first())
        }
        list.apply {
            add(POINT)
            add('0')
            add(BACKSPACE)
        }
    }

    companion object {
        private const val POINT = '.'
        private const val BACKSPACE = '⌫'
    }

}