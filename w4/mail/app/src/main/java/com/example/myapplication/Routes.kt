package com.example.myapplication

import kotlinx.serialization.Serializable

// Dữ liệu được truyền dần qua từng màn hình: email -> otp -> password
@Serializable
sealed class Destination {
    @Serializable
    data object ForgotPassword : Destination()

    @Serializable
    data class Verification(val email: String) : Destination()

    @Serializable
    data class ResetPassword(val email: String, val otp: String) : Destination()

    @Serializable
    data class Confirm(val email: String, val otp: String, val password: String) : Destination()
}

// Key để màn hình Confirm gửi kết quả ngược về màn hình ForgotPassword
const val RESULT_EMAIL = "result_email"
const val RESULT_OTP = "result_otp"
const val RESULT_PASSWORD = "result_password"
