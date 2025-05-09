package com.example.a51762_work3.Dialogs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a51762_work3.Viewmodel.MainViewModel
import com.example.a51762_work3.ui.theme.greenSmall
import com.example.a51762_work3.ui.theme.greenSmaller
import com.example.a51762_work3.ui.theme.greenText
import com.example.a51762_work3.ui.theme.mainColor

@Composable
fun FilterBrandsDialog(
    onDismissRequest: () -> Unit, // return selected letter
) {
    val mainViewModel: MainViewModel = viewModel()
    val letters = ('A'..'Z').map { it.toString() }
    var expanded by remember { mutableStateOf(false) }
    var selectedLetter by remember { mutableStateOf(letters.first()) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Filter by first Letter",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Box {
                    OutlinedTextField(
                        value = selectedLetter,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        label = { Text("Letter") },
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand")
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        letters.forEach { letter ->
                            DropdownMenuItem(
                                text = { Text(letter) },
                                onClick = {
                                    selectedLetter = letter
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text("Cancel", fontWeight = FontWeight.Bold, color = greenText)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    FilledTonalButton(onClick = {
                        mainViewModel.setFilterLetter("")
                        onDismissRequest()
                    },
                        colors = ButtonDefaults.filledTonalButtonColors(greenSmall)
                    ) {
                        Text("Clear", fontWeight = FontWeight.Bold, color = greenText)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    FilledTonalButton(onClick = {
                        mainViewModel.setFilterLetter(selectedLetter)
                        onDismissRequest()
                    },
                        colors = ButtonDefaults.filledTonalButtonColors(greenSmaller)
                    ) {
                        Text("Filter", fontWeight = FontWeight.Bold, color = greenText)
                    }
                }
            }
        }
    }
}