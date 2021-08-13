package com.app.mamochallenge.ui.mainScreen

import androidx.test.filters.SmallTest
import com.app.mamochallenge.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class MainViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun enterWholeNumber() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('4')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("36,294.00")
    }

    @Test
    fun enterWholeNumberWithZeroAsTheFirstDigit() = runBlockingTest {
        mainViewModel.onItemPressed('0')
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('4')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("36,294.00")
    }

    @Test
    fun enterDecimalNumber() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('.')
        mainViewModel.onItemPressed('7')
        mainViewModel.onItemPressed('1')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("3,629.71")
    }

    @Test
    fun enterDecimalNumberWithFourDigitsInDecimalPosition() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('.')
        mainViewModel.onItemPressed('7')
        mainViewModel.onItemPressed('1')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("3,629.71")
    }

    @Test
    fun enterDecimalNumberWithZeroInTenthsPosition() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('.')
        mainViewModel.onItemPressed('0')
        mainViewModel.onItemPressed('1')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("3,629.01")
    }

    @Test
    fun pressPointFirstThenEnterNumber() = runBlockingTest {
        mainViewModel.onItemPressed('.')
        mainViewModel.onItemPressed('4')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("0.40")
    }

    @Test
    fun pressBackspaceWithNoNumber() = runBlockingTest {
        mainViewModel.onItemPressed('⌫')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("0.00")
    }

    @Test
    fun pressBackspaceWithWholeNumber() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('4')
        mainViewModel.onItemPressed('⌫')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("3,629.00")
    }

    @Test
    fun pressBackspaceWithDecimalNumber() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('.')
        mainViewModel.onItemPressed('7')
        mainViewModel.onItemPressed('1')
        mainViewModel.onItemPressed('⌫')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("3,629.70")
    }

    @Test
    fun pressBackspaceTwiceWithDecimalNumber() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('.')
        mainViewModel.onItemPressed('7')
        mainViewModel.onItemPressed('1')
        mainViewModel.onItemPressed('⌫')
        mainViewModel.onItemPressed('⌫')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("3,629.00")
    }

    @Test
    fun pressBackspaceThriceWithDecimalNumber() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('.')
        mainViewModel.onItemPressed('7')
        mainViewModel.onItemPressed('1')
        mainViewModel.onItemPressed('⌫')
        mainViewModel.onItemPressed('⌫')
        mainViewModel.onItemPressed('⌫')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("362.00")
    }

    @Test
    fun enterDecimalNumberWithFourDigitsInDecimalPositionThenPressBackspaceTwice() = runBlockingTest {
        mainViewModel.onItemPressed('3')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('9')
        mainViewModel.onItemPressed('.')
        mainViewModel.onItemPressed('7')
        mainViewModel.onItemPressed('1')
        mainViewModel.onItemPressed('6')
        mainViewModel.onItemPressed('2')
        mainViewModel.onItemPressed('⌫')
        mainViewModel.onItemPressed('⌫')
        val number = mainViewModel.formattedNumberFlow.first().string
        assertThat(number).isEqualTo("3,629.00")
    }

}