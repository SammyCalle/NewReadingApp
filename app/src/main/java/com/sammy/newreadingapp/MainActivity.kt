package com.sammy.newreadingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sammy.newreadingapp.presentation.navigation.AppNavGraph
import com.sportwise.newreadingapp.ui.theme.NewReadingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewReadingAppTheme {
                NewsApp()
            }
        }
    }
}

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    AppNavGraph(navController = navController)
}
