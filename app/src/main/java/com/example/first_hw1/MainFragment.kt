package com.example.first_hw1

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp


var amountOfElements: MutableState<Int>? = null
const val startIndex = 0

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainWindow(savedInstanceState?.getInt("amountOfElements") ?: 0)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("amountOfElements", (amountOfElements?.value ?: 0))
    }
}

@Composable
fun MainWindow(amountOfElement: Int) {
    amountOfElements = remember { mutableIntStateOf(amountOfElement) }
    var sizeOfRow = 0
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                amountOfElements?.value = (amountOfElements?.value ?: 0) + 1
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when (LocalConfiguration.current.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                sizeOfRow = 3
            }

            Configuration.ORIENTATION_LANDSCAPE -> {
                sizeOfRow = 4
            }

            Configuration.ORIENTATION_SQUARE -> {
                sizeOfRow = 3
            }

            Configuration.ORIENTATION_UNDEFINED -> {
                sizeOfRow = 3
            }
        }
        CommonState(sizeOfRow, innerPadding)

    }
}

@Composable
fun CommonState(
    sizeOfRow: Int,
    innerPadding: PaddingValues,
) {
    val amountOfColumns = (amountOfElements!!.value) / sizeOfRow + 1
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(count = if (amountOfColumns - 1 < 0) 0 else amountOfColumns - 1) { columnAmount ->
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(count = sizeOfRow) { index ->
                        SpecialText(columnAmount * sizeOfRow + index + startIndex)
                    }
                }
            }
            item {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(count = amountOfElements!!.value % sizeOfRow + 1) { index ->
                        SpecialText((amountOfColumns - 1) * sizeOfRow + index + startIndex)
                    }
                }
            }
        }
    }
}

@Composable
fun SpecialText(index: Int) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                color = if (index % 2 == 0) Color.Red else Color.Blue,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = index.toString(),
            color = Color.White,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainWindowPreview() {
    MainWindow(0)
}
