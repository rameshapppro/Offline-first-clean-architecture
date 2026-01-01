package com.ramesh.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramesh.sample.theme.SampleTheme
import com.ramesh.sample.ui.screen.SplashScreen
import com.ramesh.sample.ui.screen.UserScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            SampleTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { scaffPadding ->
                    AppNavigation(scaffPadding)
                }
            }
        }
    }
}


// nave function
@Composable
fun AppNavigation(scaffPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = Modifier.padding(scaffPadding)){
       composable("splash") {
           SplashScreen(
               onFinished = {
                   navController.navigate("users")
               }
           )
       }
        composable("users"){
            UserScreen()
        }
    }
}

