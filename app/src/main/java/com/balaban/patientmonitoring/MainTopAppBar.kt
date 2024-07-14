package com.balaban.patientmonitoring

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balaban.patientmonitoring.ui.theme.DarkGray
import com.balaban.patientmonitoring.ui.theme.OffWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar() {
    val scrollBehavior = LocalHomePageScrollBehavior.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    var shouldShowTextField by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(shouldShowTextField) {
        if (shouldShowTextField) {
            focusRequester.requestFocus()
        }
    }

    TopAppBar(
        modifier = Modifier
            .graphicsLayer {
                val newTransactionY =
                    size.height - (size.height * (1 - scrollBehavior.state.collapsedFraction))

                translationY = -newTransactionY
                alpha = 1f - scrollBehavior.state.collapsedFraction
            },
        scrollBehavior = scrollBehavior,
        title = {

            if (shouldShowTextField) {
                TextField(
                    searchText = searchText,
                    onSearchTextChange = { searchText = it },
                    onClearClick = {
                        shouldShowTextField = false
                        searchText = ""
                    },
                    focusRequester = focusRequester
                )
            } else {
                Text(
                    text = "Top App Bar",
                    color = OffWhite,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            if (!shouldShowTextField) {
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White)
                }
            }
        },
        actions = {
            if (!shouldShowTextField) {
                IconButton(onClick = {
                    shouldShowTextField = true
                    keyboardController?.show()
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More", tint = Color.White)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            scrolledContainerColor = Color.Black
        )
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextField(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClearClick: () -> Unit,
    focusRequester: FocusRequester
) {
    val scrollBehavior = LocalHomePageScrollBehavior.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf(TextFieldValue(searchText)) }

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp)
            .focusRequester(focusRequester)
            .clickable { focusRequester.requestFocus() }
            .graphicsLayer {
                if (scrollBehavior.state.collapsedFraction <= 0) return@graphicsLayer
                val positionY = size.height - (size.height * scrollBehavior.state.collapsedFraction)
                translationY = positionY / 2
                alpha = 1f - scrollBehavior.state.collapsedFraction
            },
        onValueChange = {
            text = it
            onSearchTextChange(it.text)
        },
        trailingIcon = {
            Icon(
                Icons.Default.Clear,
                contentDescription = "Clear Button",
                modifier = Modifier.clickable {
                    onClearClick()
                    text = TextFieldValue("")
                    keyboardController?.hide()
                },
                tint = Color.Black
            )
        },
        placeholder = { Text(text = "Search...", color = DarkGray) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.DarkGray,
            unfocusedBorderColor = Color.White,
            cursorColor = Color.Black,
            containerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(16)
    )
}