package com.example.littlelemon.composable

import Onboarding
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.AppDatabase
import com.example.littlelemon.navigation.Home
import com.example.littlelemon.navigation.Onboard
import com.example.littlelemon.navigation.Profile

@Composable
fun NavigationComposable(navController: NavHostController, database: AppDatabase, modifier: Modifier) {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    val email = sharedPreferences.getString("email", "")
    var startDestination = Onboard.route
     if (email?.isEmpty() == false)
        startDestination = Home.route

    NavHost(navController, startDestination = startDestination, modifier = modifier) {
        composable(route = Home.route) { Home(navController, database) }
        composable(route = Profile.route) { Profile(navController) }
        composable(route = Onboard.route) { Onboarding(navController) }
    }

}