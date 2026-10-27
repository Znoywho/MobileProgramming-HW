package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(darkTheme = false, dynamicColor = false) {
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
        startDestination = Destination.ForgotPassword,
        builder = {
            composable<Destination.ForgotPassword> { entry ->
                // Đọc dữ liệu màn hình Confirm trả về (null nếu chưa submit)
                val state = entry.savedStateHandle
                val email by state.getStateFlow<String?>(RESULT_EMAIL, null).collectAsState()
                val otp by state.getStateFlow<String?>(RESULT_OTP, null).collectAsState()
                val password by state.getStateFlow<String?>(RESULT_PASSWORD, null).collectAsState()
                ForgotPasswordScreen(
                    resultEmail = email,
                    resultOtp = otp,
                    resultPassword = password,
                    onNext = { navController.navigate(Destination.Verification(it)) }
                )
            }
            composable<Destination.Verification> { entry ->
                val args = entry.toRoute<Destination.Verification>()
                VerificationScreen(
                    email = args.email,
                    onBack = { navController.popBackStack() },
                    onNext = { navController.navigate(Destination.ResetPassword(args.email, it)) }
                )
            }
            composable<Destination.ResetPassword> { entry ->
                val args = entry.toRoute<Destination.ResetPassword>()
                ResetPasswordScreen(
                    onBack = { navController.popBackStack() },
                    onNext = { navController.navigate(Destination.Confirm(args.email, args.otp, it)) }
                )
            }
            composable<Destination.Confirm> { entry ->
                val args = entry.toRoute<Destination.Confirm>()
                ConfirmScreen(
                    email = args.email,
                    otp = args.otp,
                    password = args.password,
                    onBack = { navController.popBackStack() },
                    onSubmit = {
                        // Gửi email/otp/pass về màn hình 1 rồi quay lại đó
                        navController.getBackStackEntry<Destination.ForgotPassword>().savedStateHandle.apply {
                            set(RESULT_EMAIL, args.email)
                            set(RESULT_OTP, args.otp)
                            set(RESULT_PASSWORD, args.password)
                        }
                        navController.popBackStack(Destination.ForgotPassword, inclusive = false)
                    }
                )
            }
        }
    )
}
