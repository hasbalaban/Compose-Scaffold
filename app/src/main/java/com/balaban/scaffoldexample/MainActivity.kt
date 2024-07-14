package com.balaban.scaffoldexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContextCompat
import com.balaban.scaffoldexample.ui.theme.ScaffoldExampleTheme

@OptIn(ExperimentalMaterial3Api::class)
val LocalHomePageScrollBehavior =
    compositionLocalOf<TopAppBarScrollBehavior> { error("No TopAppBarScrollBehavior found") }


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                ContextCompat.getColor(this, R.color.black)
            ),
            navigationBarStyle = SystemBarStyle.light(
                ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.black),
            )
        )

        setContent {
            ScaffoldExampleTheme {
                val scrollBehavior =
                    TopAppBarDefaults.enterAlwaysScrollBehavior(snapAnimationSpec = spring(stiffness = Spring.StiffnessLow))

                Surface(color = Color.Black) {
                    CompositionLocalProvider(LocalHomePageScrollBehavior provides scrollBehavior) {
                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .nestedScroll(scrollBehavior.nestedScrollConnection),
                            topBar = { MainTopAppBar() },
                            bottomBar = { MainBottomAppBar() },
                            containerColor = Color.Transparent
                        ) { innerPadding ->
                            MainScreen(
                                modifier = Modifier.padding(
                                    PaddingValues(
                                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                                        top = innerPadding.calculateTopPadding()
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { 10 })

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.Transparent)) {

        TabRowScreen(pagerState = pagerState)

        HorizontalPager(state = pagerState) {
            HorizontalScreenItem()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScaffoldExampleTheme {
        MainScreen()
    }
}