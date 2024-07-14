package com.balaban.patientmonitoring

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomAppBar() {
    val scrollBehavior = LocalHomePageScrollBehavior.current
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    BottomAppBar(
        tonalElevation = 8.dp,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                val positionY =
                    size.height - (size.height * (1 - scrollBehavior.state.collapsedFraction))
                translationY = positionY
                alpha = 1f - scrollBehavior.state.collapsedFraction
            }

    ) {
        NavigationBar(containerColor = Color.Black) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.first, contentDescription = item.second) },
                    label = { Text(item.second, fontSize = 10.sp) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.White.copy(0.7f),
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White.copy(0.7f),
                        indicatorColor = Color(0xffffffff)
                    )
                )
            }
        }
    }
}