package com.example.smarttasks.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarttasks.ui.screen.Confirm
import com.example.smarttasks.ui.screen.Createnewpass
import com.example.smarttasks.ui.screen.ForgetPassword
import com.example.smarttasks.ui.screen.Srceendetail
import com.example.smarttasks.ui.screen.VerifyCode
import com.example.smarttasks.ui.viewmodel.ForgetPasswordViewModel

@Composable
fun MyAppNavigation(){
    val navController = rememberNavController()
    val forgetPasswordVM: ForgetPasswordViewModel = viewModel()
    NavHost(navController = navController, startDestination = Routes.forgerpass, builder = {
        composable(Routes.forgerpass){
            ForgetPassword(onNext = {email -> navController.navigate("${Routes.verifycode}/$email")})
        }

        composable("${Routes.verifycode}/{email}",){
            val email = it.arguments?.getString("email") ?: ""
            VerifyCode(email = email,onNext = {navController.navigate(Routes.createnewpass)},
                onBack = { navController.popBackStack() })
        }

        composable(Routes.createnewpass) {
            Createnewpass(viewModel = forgetPasswordVM, onNext = {navController.navigate(Routes.comfirmpass)},
                onBack = {navController.popBackStack()})
        }

        composable(Routes.comfirmpass) {
            Confirm(viewModel = forgetPasswordVM, onNext = {navController.navigate(Routes.detail)},
                onBack = {navController.popBackStack()})
        }

        composable(Routes.detail) {
            Srceendetail(viewModel = forgetPasswordVM)
        }
    })
}