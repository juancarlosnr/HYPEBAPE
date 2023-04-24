package com.example.hypebape.presentation.login_screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hypebape.ContenedorPrincipal
import com.example.hypebape.R
import com.example.hypebape.navigation.Screens
import com.example.hypebape.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (context as? Activity)?.finish()
        }
    }
    dispatcher?.addCallback(onBackPressedCallback)
    // Agrega el OnBackPressedCallback al onBackPressedDispatcher

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    val state = viewModel.signInState.collectAsState(initial = null)

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = null,
            modifier = Modifier
                .width(2000.dp)
                .height(1400.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "HYPEBAPE",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 30.dp),
                fontFamily = RegularFont,
                fontWeight = FontWeight.Bold
            )


        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            val painter: Painter = painterResource(id = R.drawable.loginimage)
            Image(
                painter = painter,
                contentDescription = "prueba",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Correo:",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 12.dp, start = 8.dp),
                fontSize = 18.sp,
                fontFamily = RegularFont,
                color = Color.White
            )
            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(width = 1.dp, color = buttomBorder, shape = RoundedCornerShape(12.dp))
                    .height(52.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(buttomDegradado1, buttomDegradado2),
                            start = Offset(100f, 0f),
                            end = Offset(900f, 0f)
                        )
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White,
                    disabledLabelColor = lightBlue, unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    textColor = Color.White
                ), shape = RoundedCornerShape(8.dp), singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Contraseña:",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 12.dp, start = 8.dp),
                fontSize = 18.sp,
                fontFamily = RegularFont,
                color = Color.White
            )
            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(width = 1.dp, color = buttomBorder, shape = RoundedCornerShape(12.dp))
                    .height(52.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(buttomDegradado1, buttomDegradado2),
                            start = Offset(100f, 0f),
                            end = Offset(900f, 0f)
                        )
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    disabledLabelColor = lightBlue, unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ), shape = RoundedCornerShape(8.dp), singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        Toast
                            .makeText(context, "Olvidar contraseña", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .padding(top = 15.dp),
                text = "¿Has olvidado tu contraseña? ",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = RegularFont,

                )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(2.dp, buttomBorder, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                contentPadding = PaddingValues(),
                onClick = {
                    scope.launch {
                        viewModel.loginUser(email, password)
                    }
                }
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
                    Text(text = "INICIAR SESIÓN", color = Color.White, fontSize = 18.sp)
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (state.value?.isLoading == true) {
                    CircularProgressIndicator()
                }

            }
            Text(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screens.SignUpScreen.route)
                    }
                    .padding(top = 15.dp),
                text = "Nuevo usuario? Registrate ",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = RegularFont,

                )
            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        navegarAContenedorPrincipal(contexto = context)
                        //navController.navigate(Screens.ContenedorPrincipal.route)
                    }
                }
            }

            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast
                            .makeText(context, "${error}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

        }
    }

}
fun navegarAContenedorPrincipal(contexto: Context) {
    val intent = Intent(contexto, ContenedorPrincipal::class.java)
    contexto.startActivity(intent)
}
