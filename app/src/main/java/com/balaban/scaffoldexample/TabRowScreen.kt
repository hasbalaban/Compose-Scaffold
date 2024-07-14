package com.balaban.scaffoldexample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabRowScreen(pagerState: PagerState) {
    val coroutines = rememberCoroutineScope()

    ScrollableTabRow(selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.fillMaxWidth(),
        edgePadding = 16.dp,
        containerColor = Color.Black,
        indicator = { tabPositions ->
            if (pagerState.currentPage < tabPositions.size) {
                SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color(0xff1d9bf0)
                )
            }
        },
        divider = { HorizontalDivider(color = DividerDefaults.color.copy(0.5f)) }
    ) {
        homeTabRows.forEachIndexed { index, value ->
            Tab(
                modifier = Modifier.padding(horizontal = 12.dp),
                selected = pagerState.currentPage == index,
                onClick = { coroutines.launch { pagerState.animateScrollToPage(index) } }) {
                Text(
                    text = value,
                    color = Color.White,
                    maxLines = 1,
                    modifier = Modifier.graphicsLayer {
                        alpha = if (pagerState.currentPage == index) 1f else 0.6f
                    }
                )
            }
        }
    }
}

