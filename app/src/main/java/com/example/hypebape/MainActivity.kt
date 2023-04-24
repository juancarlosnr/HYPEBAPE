package com.example.hypebape

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hypebape.navigation.NavigationGraph
import com.example.hypebape.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch


val Context.dataStore by preferencesDataStore(name = "PREFERENCES")

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private val mainActivityViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainActivityViewModel.splashLoading

        }
        lifecycleScope.launch {
            mainActivityViewModel.getUser()


        }
        setContent {
            navHostController = rememberNavController()
            NavigationGraph(navHostController)
            mainActivityViewModel.navegarALogin.observe(this) { navegarLogin ->
                if (navegarLogin) {
                    navHostController.navigate(Screens.SignInScreen.route)
                } else {
                    Log.d("navegarLogin", "No vamos a navegar")
                }
            }
            var prueba = mainActivityViewModel.getUserState.collectAsState(initial = null)
            if (!prueba.value?.isSuccess.isNullOrEmpty()) {
                var intent = Intent(this,ContenedorPrincipal::class.java )
                startActivity(intent)
                Log.d("holaquetal", prueba.value?.isSuccess.toString())
            }

        }

        mainActivityViewModel.getOnBoarding(this)
        mainActivityViewModel.checkAuthentication()
    }

}

