package com.app.mamochallenge.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mamochallenge.models.FormattedNumber
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
                enteredNumberFlow.emit(currentNumber.dropLast(1))
            }
            else -> if (
                !currentNumber.contains(POINT)
                || currentNumber.substringAfter(POINT).length < 2
            ) {
                if (currentNumber.contains(POINT) || currentNumber.length < 9) {
                    enteredNumberFlow.emit("$currentNumber$item")
                }
            }
        }
    }

    private fun formatNumber(numberToFormat: String): FormattedNumber {
        val realNumber = numberToFormat.let { number ->
            if (number.isEmpty()) "0" else number.dropLastWhile { it == POINT }
        }
        val wholePart = realNumber.substringBefore(POINT).toInt()
        val formattedWholePart = String.format("%,d", wholePart)
        val decimalPart = realNumber.substringAfter(POINT, "")
        val tenths: String
        val hundredths: String
        val hasTenths: Boolean
        val hasHundredths: Boolean
        when {
            decimalPart.isEmpty() -> {
                tenths = "0"
                hundredths = "0"
                hasTenths = false
                hasHundredths = false
            }
            decimalPart.length == 1 -> {
                tenths = decimalPart[0].toString()
                hundredths = "0"
                hasTenths = true
                hasHundredths = false
            }
            else -> {
                tenths = decimalPart[0].toString()
                hundredths = decimalPart[1].toString()
                hasTenths = true
                hasHundredths = true
            }
        }
        return FormattedNumber(
            formattedWholePart,
            tenths,
            hundredths,
            numberToFormat.contains(POINT),
            hasTenths,
            hasHundredths
        )
    }

    companion object {
        private const val POINT = '.'
        private const val BACKSPACE = '⌫'
    }

}