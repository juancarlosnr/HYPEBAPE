package com.example.hypebape

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hypebape.data.bottomnav.NavigationItem
import com.example.hypebape.presentation.detail_screen.DetailScreen
import com.example.hypebape.screens.HomeScreen
import com.example.hypebape.screens.ProfileScreen
import com.example.hypebape.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContenedorPrincipal : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("pruebaLogin", "esto es una prueba de login")
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Navigation()
            }
        }
    }
}


@Composable
fun NavigationController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {

        composable(NavigationItem.Home.route) {
            HomeScreen(hiltViewModel(), navController)
        }

        composable(NavigationItem.Notifications.route) {
            Notifications()
        }

        composable(NavigationItem.Settings.route) {
            Settings()
        }

        composable(NavigationItem.Account.route) {
            ProfileScreen()
        }
        composable(
            NavigationItem.Detail.route,
            arguments = listOf(navArgument("idSneaker") { type = NavType.IntType })
        ) {
            DetailScreen(navController)
        }



    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navigation() {

    val navController = rememberNavController()

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Settings,
        NavigationItem.Notifications,
        NavigationItem.Account
    )
    val gradient = Brush.horizontalGradient(
        colors = listOf(bottomNavBar1, Color.Black, Color.White),
        startX = 0.0f,
        endX = Float.POSITIVE_INFINITY,
        tileMode = TileMode.Clamp
    )
    val navBackStackEntry1 by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry1?.destination?.route.toString()
    Log.d("rutaActualNav", currentRoute)
    Scaffold(

        bottomBar = {
            if(!currentRoute.equals("detail/{idSneaker}")){
            BottomNavigation(
                modifier = Modifier.background(
                    brush = Brush.linearGradient(
                        colors = listOf(buttomDegradado1, buttomDegradado2),
                        start = Offset(400f, 0f),
                        end = Offset(900f, 0f),
                    )
                ),
                backgroundColor = Color.Transparent,
            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach {
                    BottomNavigationItem(
                        selected = currentRoute == it.route,
                        label = {
                            Text(
                                text = it.label,
                                color = if (currentRoute == it.route) Color.DarkGray else Color.LightGray
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = it.icons, contentDescription = null,
                                tint = if (currentRoute == it.route) Color.DarkGray else Color.LightGray
                            )

                        },

                        onClick = {
                            if (currentRoute != it.route) {

                                navController.graph?.startDestinationRoute?.let {
                                    navController.popBackStack(it, true)
                                }

                                navController.navigate(it.route) {
                                    launchSingleTop = true
                                }

                            }

                        })

                }


            }
        }}) {

        NavigationController(navController = navController)

    }

}


@Composable
fun Notifications() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Notifications")

        }
    }
}

@Composable
fun Settings() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Settings")

        }
    }
}

