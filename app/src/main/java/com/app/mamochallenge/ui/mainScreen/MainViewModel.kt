package com.app.mamochallenge.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

    private val lastPressedItemFlow = MutableSharedFlow<Char>()

    private val enteredNumberFlow = MutableStateFlow("")

    val formattedNumberFlow = enteredNumberFlow.map {
        formatNumber(it)
    }

    init {
        viewModelScope.launch {
            lastPressedItemFlow.collect {
                handlePressedItem(it)
            }
        }
    }

    fun onItemPressed(item: Char) = viewModelScope.launch {
        lastPressedItemFlow.emit(item)
    }

    private suspend fun handlePressedItem(item: Char) {
        val currentNumber = enteredNumberFlow.first()
        when (item) {
            POINT -> if (currentNumber.lastOrNull() != POINT) {
                val wholePart = if (currentNumber.isEmpty()) {
                    0
                } else {
                    currentNumber
                }
                enteredNumberFlow.emit("$wholePart$POINT")
            }
            BACKSPACE -> if (currentNumber.isNotEmpty()) {
                val digitsToDrop = if (currentNumber.endsWith(POINT)) 2 else 1
                enteredNumberFlow.emit(currentNumber.dropLast(digitsToDrop))
            }
            else -> if (
                !currentNumber.contains(POINT)
                || currentNumber.substringAfter(POINT).length < 2
            ) {
                enteredNumberFlow.emit("$currentNumber$item")
            }
        }
    }

    private fun formatNumber(numberToFormat: String): String {
        val realNumber = numberToFormat.let { number ->
            if (number.isEmpty()) "0" else number.dropLastWhile { it == POINT }
        }
        val wholePart = realNumber.substringBefore(POINT).toInt()
        val formattedWholePart = String.format("%,d", wholePart)
        val decimalPart = realNumber
            .substringAfter(POINT, "00")
            .let {
                if (it.length == 1) "$it${0}" else it
            }
        return "$formattedWholePart$POINT$decimalPart"
    }

    companion object {
        private const val POINT = '.'
        private const val BACKSPACE = '⌫'
    }

}