package com.app.mamochallenge.ui.mainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.mamochallenge.models.FormattedNumber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val number = mainViewModel.formattedNumberFlow.collectAsState(
        FormattedNumber("0", "0", "0", false)
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            MultipleStylesInText(formattedNumber = number.value)
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyVerticalGrid(
            cells = GridCells.Fixed(3)
        ) {
            items(mainViewModel.keyboardItemsList) { item ->
                Box(
                    modifier = Modifier.height(104.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp),
                        shape = CircleShape,
                        onClick = {
                            mainViewModel.onItemPressed(item)
                        }
                    ) {
                        Text(
                            text = item.toString(),
                            fontSize = 24.sp,
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun MultipleStylesInText(formattedNumber: FormattedNumber) {
    val wholePart = formattedNumber.wholePart
    val isDecimalEnabled = formattedNumber.isDecimalEnabled
    val tenths = formattedNumber.tenths
    val hundredths = formattedNumber.hundredths
    Text(
        text = buildAnnotatedString {
            append("\u200E")
            val wholePartColor = getSuitableTextColor(wholePart != "0")
            withStyle(style = SpanStyle(color = wholePartColor)) {
                append("AED ")
                append(wholePart)
            }
            val pointColor = getSuitableTextColor(isDecimalEnabled)
            withStyle(style = SpanStyle(color = pointColor)) {
                append('.')
            }
            val tenthsPartColor = getSuitableTextColor(tenths != "0")
            withStyle(style = SpanStyle(color = tenthsPartColor)) {
                append(tenths)
            }
            val hundredthsPartColor = getSuitableTextColor(hundredths != "0")
            withStyle(style = SpanStyle(color = hundredthsPartColor)) {
                append(hundredths)
            }
        },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .semantics { contentDescription = "NumberText" },
        fontSize = 48.sp
    )
}

@Composable
private fun getSuitableTextColor(isEnabled: Boolean): Color {
    return when {
        isEnabled -> MaterialTheme.colors.onBackground
        isSystemInDarkTheme() -> Color.DarkGray
        else -> Color.LightGray
    }
}