package com.example.hypebape.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.hypebape.R
import com.example.hypebape.data.bottomnav.NavigationItem
import com.example.hypebape.data.home.Advertising
import com.example.hypebape.data.home.Brand
import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.presentation.home_screen.HomeViewModel
import com.example.hypebape.ui.theme.buttomBorder
import com.example.hypebape.ui.theme.buttomDegradado1
import com.example.hypebape.ui.theme.buttomDegradado2
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavHostController) {
    var favoriteList by remember { mutableStateOf(emptyList<Int>()) }
    var sneakerList by remember { mutableStateOf(emptyList<Sneaker>()) }
    var brandsList by remember { mutableStateOf(emptyList<Brand>()) }
    var advertising by remember { mutableStateOf(emptyList<Advertising>()) }
    LaunchedEffect(Unit) {
        viewModel.getFavorites()
        viewModel.getSneakers()
        viewModel.getBrands()
        viewModel.getAdvertising()
    }
    /* val sneakersSearch by viewModel.sneakers2.collectAsState()
     viewModel.sneakers2.collectAsState().value*/
    viewModel.sneakersList.observeAsState().value?.let {
        sneakerList = it
    }
    viewModel.brandsList.observeAsState().value?.let {
        brandsList = it
    }
    viewModel.advertising.observeAsState().value?.let {
        advertising = it
    }
    viewModel.favorites.observeAsState().value?.let {
        favoriteList = it
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_home),
            contentDescription = null,
            modifier = Modifier
                .width(2000.dp)
                .height(1400.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            SearchView(textState, viewModel)
            Spacer(modifier = Modifier.height(8.dp))
            ImagenAnuncio(advertising)
            Spacer(modifier = Modifier.height(16.dp))
            RecyclerViewMarcas(brandsList, viewModel)
            Spacer(modifier = Modifier.height(8.dp))
            RecyclerCartas(favoriteList,sneakerList, viewModel, navController)
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImagenAnuncio(listAds: List<Advertising>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val state = rememberPagerState()

        HorizontalPager(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            count = listAds.size

        ) { page ->
            ImagePager(urlPhoto = listAds[page].urlAdvertising)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            repeat(listAds.size) {
                val color =
                    if (state.currentPage == it) buttomBorder else buttomBorder.copy(alpha = 0.3f)
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(12.dp)
                        .background(color)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


@Composable
fun ImagePager(urlPhoto: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(width = 3.dp, color = buttomBorder, shape = RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            model = urlPhoto,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>, viewModel: HomeViewModel) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Box(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp), // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                leadingIconColor = Color.White,
                trailingIconColor = Color.White,
                backgroundColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }

}


@Composable
fun CirculoMarcas(brand: Brand, onItemSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(buttomDegradado1, buttomDegradado2),
                    start = Offset(100f, 0f),
                    end = Offset(900f, 0f),
                )
            )
            .clickable {
                onItemSelected(brand.name)
            }
    ) {
        Log.d("brand", brand.name)

        AsyncImage(
            model = brand.urlPhoto,
            contentDescription = "Imagen ${brand.name}",
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentScale = ContentScale.Crop

        )

    }
}

@Composable
fun RecyclerViewMarcas(brandsList: List<Brand>, viewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(50.dp)
    ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(brandsList) { brand ->
                CirculoMarcas(brand) { viewModel.getSneakersByBrands(it) }
            }
        }
    }
}


@Composable
fun RecyclerCartas(
    favoriteList: List<Int>,
    listSneakers: List<Sneaker>,
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(listSneakers) { sneaker ->
            val favorites = favoriteList.map { it.toString().trim() }
            if(favorites.contains(sneaker.id.toString())){
                CardShoes(true,sneaker, navController) {
                    viewModel.setOrDeleteFavorite(it)
                }
            }else{
                CardShoes(false,sneaker, navController) {
                    viewModel.setOrDeleteFavorite(it)
                }
            }

        }
    }
}


@Composable
fun CardShoes(
    favorite:Boolean,
    sneaker: Sneaker,
    navController: NavHostController,
    onFavoriteSelected: (Int) -> Unit,
) {
    var favorito = favorite
    var icon by remember { mutableStateOf(if(favorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder) }
    Card(
        modifier = Modifier
            .width(60.dp)
            .height(200.dp)
            .clickable {
                navController.navigate(NavigationItem.Detail.createRoute(sneaker.id))
            }
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
                    icon,
                    contentDescription = "Prueba",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        onFavoriteSelected(sneaker.id)
                        favorito = !favorito
                        icon = if(favorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder

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
                    contentDescription = "Imagen ${sneaker.name}",
                    modifier = Modifier
                        .fillMaxSize()

                )

            }

        }
    }
}
