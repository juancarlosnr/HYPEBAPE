package com.example.hypebape

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hypebape.navigation.NavigationGraph
import com.example.hypebape.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint


val Context.dataStore by preferencesDataStore(name = "PREFERENCES")
@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MainViewModel(this)
        installSplashScreen().setKeepOnScreenCondition{
            viewModel.splashLoading
        }




        setContent {
            navHostController = rememberNavController()
            NavigationGraph(navHostController)
            viewModel.navegarALogin.observe(this) { navegarLogin ->
                if (navegarLogin) {
                    navHostController.navigate(Screens.SignUpScreen.route)
                } else {
                    Log.d("navegarLogin", "No vamos a navegar")
                }
            }
            viewModel.getOnBoarding()
            viewModel.checkAuthentication()
        }
    }


}
