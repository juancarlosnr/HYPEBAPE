package com.example.hypebape.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object OnBoardingScreen: Screens(route = "OnBoarding")
}
