package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                NavigationApp()
            }
        }
    }
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.Splash,
        builder = {
            composable<Destination.Splash> {
                SplashScreen(navController)
            }
            composable<Destination.Onboarding1> {
                ContentScreen(navController, OnboardingPage.Step1)
            }
            composable<Destination.Onboarding2> {
                ContentScreen(navController, OnboardingPage.Step2)
            }
            composable<Destination.Onboarding3> {
                ContentScreen(navController, OnboardingPage.Step3)
            }
            composable<Destination.Home> {
                HomeScreen()
            }
        }
    )
}
