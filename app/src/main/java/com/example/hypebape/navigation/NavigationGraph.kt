package com.example.hypebape.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hypebape.presentation.login_screen.SignInScreen
import com.example.hypebape.presentation.onboarding.OnBoarding
import com.example.hypebape.presentation.signup_screen.SignUpScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.OnBoardingScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(route = Screens.OnBoardingScreen.route){
            OnBoarding(navController)
        }

    }

}