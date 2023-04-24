package com.example.hypebape.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import com.example.hypebape.R
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hypebape.data.profile.User
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import coil.compose.AsyncImage
import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.presentation.profile_screen.ProfileViewModel
import com.example.hypebape.ui.theme.RegularFont
import com.example.hypebape.ui.theme.buttomBorder
import com.example.hypebape.ui.theme.buttomDegradado1
import com.example.hypebape.ui.theme.buttomDegradado2


@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    var user by remember { mutableStateOf(User("", "", "", ArrayList())) }
    var sneakerList by remember { mutableStateOf(emptyList<Sneaker>()) }

    LaunchedEffect(Unit) {
        viewModel.getDataUser()
        viewModel.getFavoritesSneakers()
    }
    viewModel.userData.observeAsState().value?.let {
        user = it
    }
   viewModel.listSneakers.observeAsState().value?.let {
        sneakerList = it

    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (user == null || user.name.isEmpty()) {
            // Agregar ProgressBar que ocupe toda la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(buttomDegradado1, buttomDegradado2),
                            start = Offset(100f, 0f),
                            end = Offset(900f, 0f)
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp),
                    color = Color.White
                )
                Text(
                    "Cargando, por favor espere un momento",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Default,
                )
            }

        } else {
            Image(
                painter = painterResource(id = R.drawable.background_profile),
                contentDescription = null,
                modifier = Modifier
                    .width(2000.dp)
                    .height(2000.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(26.dp)
                        .align(Alignment.End),
                    tint = Color.White
                )
                Text(
                    text = user.name,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = RegularFont,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                AsyncImage(
                    model = user.urlPhoto,
                    contentDescription = "Translated description of what the image contains",
                    modifier = Modifier
                        .width(185.dp)
                        .height(180.dp)
                        .clip(CircleShape)
                        .border(width = 3.dp, color = buttomBorder, shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "FAVORITOS",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = RegularFont,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                RecyclerCartasProfile(sneakerList, viewModel)
            }
        }
    }
}

@Composable
fun RecyclerCartasProfile(sneakerList: List<Sneaker>, viewModel: ProfileViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sneakerList) { sneaker ->
            CardShoesProfile(sneaker){viewModel.deleteFavorite(it)}
        }
    }
}


@Composable
fun CardShoesProfile(sneaker: Sneaker,onDeleteSelected:(Int) -> Unit) {
    val painter: Painter = painterResource(id = R.drawable.pruebazapa)
    Card(
        modifier = Modifier
            .width(60.dp)
            .height(200.dp)
            .border(width = 3.dp, color = buttomBorder, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.Red,
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .width(60.dp)
                .height(200.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(buttomDegradado1, buttomDegradado2),
                        start = Offset(100f, 0f),
                        end = Offset(900f, 0f),
                    ),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(end = 16.dp, top = 8.dp), horizontalAlignment = Alignment.End

            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Prueba",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        onDeleteSelected(sneaker.id)
                    }
                )
            }

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                AsyncImage(
                    model = sneaker.urlphoto,
                    contentDescription = "Sneaker ${sneaker.name}",
                    modifier = Modifier
                        .fillMaxSize()

                )

            }


        }
    }
}

