package com.example.hypebape.presentation.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hypebape.R
import com.example.hypebape.navigation.Screens
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun OnBoarding(navController: NavController) {

    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_home),
                contentDescription = null,
                modifier = Modifier
                    .width(2000.dp)
                    .height(1400.dp),
                contentScale = ContentScale.Crop
            )
        TopSection(navController)

        val items = OnBoardingItem.get()
        val state = rememberPagerState()

            HorizontalPager(
                state = state,
                modifier = Modifier
                    .fillMaxSize(),
                count = items.size

            ) { page ->

                OnBoardingItem(items[page])

            }


        BottomSection(size = items.size, index = state.currentPage) {
            if (state.currentPage + 1 < items.size)
                scope.launch {
                    state.scrollToPage(state.currentPage + 1)
                }
            if (state.currentPage == 2) {
                navController.navigate(Screens.SignInScreen.route)
            }
        }
        }
    }

}

@Composable
fun TopSection(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)

    ) {

        //skip button
        TextButton(
            onClick = { navController.navigate(Screens.SignInScreen.route) },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text("Skip", color = MaterialTheme.colors.onBackground)
        }

    }
}

@Composable
fun BottomSection(
    size: Int,
    index: Int,
    onNextClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        //indicators
        Indicators(size = size, index = index)

        //next button
        FloatingActionButton(
            onClick = onNextClicked,
            modifier = Modifier.align(Alignment.CenterEnd),
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.onPrimary
        ) {
            Icon(Icons.Outlined.KeyboardArrowRight, null, tint = Color.Black)
        }

    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}


@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) Color.White
                else Color.Gray.copy(alpha = 0.5f)
            )
    ) {

    }
}

@Composable
fun OnBoardingItem(
    item: OnBoardingItem
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(item.image), contentDescription = null)
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(item.title),
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(item.text),
            color =Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}