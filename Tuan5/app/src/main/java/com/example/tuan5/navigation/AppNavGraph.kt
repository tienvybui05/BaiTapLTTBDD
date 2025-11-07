package com.example.tuan5.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tuan5.auth.LoginScreen
import com.example.tuan5.profile.DetailProductScreen
import com.example.tuan5.profile.ProfileScreen
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavGraph(
    auth: FirebaseAuth,
    googleSignInClient: GoogleSignInClient
) {
    val navController = rememberNavController()
    val startDestination = if (auth.currentUser != null) "profile" else "login"

    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") {
            LoginScreen(
                googleSignInClient = googleSignInClient,
                auth = auth,
                onSignInSuccess = {
                    navController.navigate("profile") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                auth = auth,
                onSignOut = {
                    auth.signOut()
                    googleSignInClient.signOut()
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            )
        }

        composable("detail_product") { DetailProductScreen(navController) }
    }
}
