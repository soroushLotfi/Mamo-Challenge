package com.app.mamochallenge.ui.mainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = "123",
                fontSize = 48.sp,
                color = MaterialTheme.colors.onBackground
            )
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