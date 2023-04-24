package com.example.hypebape.presentation.detail_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.hypebape.R
import com.example.hypebape.data.home.Sneaker
import coil.compose.AsyncImage
import com.example.hypebape.ui.theme.buttomBorder
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.example.hypebape.ui.theme.RegularFont
import com.example.hypebape.ui.theme.buttomDegradado1
import com.example.hypebape.ui.theme.buttomDegradado2
import com.example.hypebape.ui.theme.buttonBack
import com.example.hypebape.ui.theme.buttonBackBorder
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    var sneaker by remember { mutableStateOf(Sneaker()) }
    var bottomBarVisible by remember { mutableStateOf(true) } // variable para controlar la visibilidad de la bottom navigation
    var imageList by remember { mutableStateOf(emptyList<String>()) }
    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.arguments?.getInt("idSneaker")
            ?.let { viewModel.getDetailSneaker(it) }
        bottomBarVisible = false // ocultar la bottom navigation al abrir la pantalla de detalles
    }

    viewModel.sneaker.observeAsState().value?.let {
        sneaker = it
        imageList = it.urlPhotos
        Log.d("sneakerPhoto", sneaker.urlphoto)
        Log.d("sneakersPhoto", sneaker.brand)
    }

    Scaffold(

        bottomBar = {
            if (bottomBarVisible) { // mostrar o no la bottom navigation según el valor de bottomBarVisible
                // contenido de la bottom nav
            }
        }
    ) {
        // contenido de la pantalla de detalles
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_detail),
                contentDescription = null,
                modifier = Modifier
                    .width(2000.dp)
                    .height(1400.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                ButtonGoBack(navController)
                RecyclerImages(imageList)
                MainText(sneaker.name)
                SecondaryText(sneaker.brand)
                DescriptionText("aksdaksdkads")
                DateText("19-08-2022")
                ButtonsSaleAndResell("", "")
                ButtonBuy("link")
            }
        }
    }
}

@Composable
fun ButtonsSaleAndResell(priceLaunch: String, priceResell: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Elemento de la izquierda
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            ButtonBuyAndResell(priceLaunch)
        }

        // Elemento de la derecha
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            ButtonBuyAndResell(priceResell)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ButtonBuyAndResell(price: String) {

        Box(
            modifier = Modifier
                .width(170.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, buttomBorder, RoundedCornerShape(10.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(buttomDegradado1, buttomDegradado2),
                        start = Offset(100f, 0f),
                        end = Offset(900f, 0f),
                    )
                ),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Precio",
                    color = Color.White,
                    fontSize = 15.sp,
                )
                Text(
                    text = "Lanzamiento",
                    color = Color.White,
                    fontSize = 15.sp,
                )
                Text(
                    text = "230",
                    color = Color.White,
                    fontSize = 15.sp,

                    )
            }

    }
}


@Composable
fun ButtonBuy(link: String) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(2.dp, buttomBorder, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { //Navegamos hacía el link
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, buttomBorder, RoundedCornerShape(10.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(buttomDegradado1, buttomDegradado2),
                        start = Offset(100f, 0f),
                        end = Offset(900f, 0f),
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "COMPRAR", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun DateText(date: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = date,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = RegularFont,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(12.dp))

}


@Composable
fun DescriptionText(description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = description,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = RegularFont
        )
    }
}

@Composable
fun SecondaryText(brand: String) {
    Spacer(modifier = Modifier.height(1.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = brand,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = RegularFont
        )
    }
}

@Composable
fun MainText(name: String) {
    Spacer(modifier = Modifier.height(12.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = RegularFont,
            fontWeight = FontWeight.Bold
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecyclerImages(listImages: List<String>) {
    val state = rememberPagerState()
    var selectedPageIndex by remember { mutableStateOf(0) } // índice de la imagen seleccionada
    val coroutineScope = rememberCoroutineScope()
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        HorizontalPager(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            count = listImages.size

        ) { page ->
            MainImage(urlPhoto = listImages[page])
        }
    }
    Spacer(modifier = Modifier.height(2.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(listImages.size) { index ->
            val color =
                if (state.currentPage == index) buttomBorder else buttomBorder.copy(alpha = 0.3f)
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .border(2.dp, color, CircleShape)
                    .clickable {
                        selectedPageIndex = index
                        coroutineScope.launch {
                            state.scrollToPage(index)
                        }
                    }
            ) {
                AsyncImage(
                    model = listImages[index],
                    contentDescription = null,
                    Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}


@Composable
fun MainImage(urlPhoto: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = urlPhoto,
            contentDescription = null,
        )
    }

}

@Composable
fun ButtonGoBack(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Elemento de la izquierda
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.TopStart
        ) {
            Box(
                modifier = Modifier
                    .background(buttonBack, shape = CircleShape)
                    .size(48.dp)
                    .clickable { navController.navigateUp() }
                    .border(2.dp, buttonBackBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Botón de regreso",
                    tint = Color.White
                )
            }
        }

        // Elemento de la derecha
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .background(buttonBack, shape = CircleShape)
                    .size(48.dp)
                    .clickable {}
                    .border(2.dp, buttonBackBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Botón de regreso",
                    tint = Color.White
                )
            }
        }
    }

}






