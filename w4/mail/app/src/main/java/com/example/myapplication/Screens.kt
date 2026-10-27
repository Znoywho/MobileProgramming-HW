package com.example.myapplication

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.SmartBlueLight
import com.example.myapplication.ui.theme.UthRed

private const val OTP_LENGTH = 5

// Màn hình 1: nhập email. Sau khi Confirm submit, hiển thị email/otp/pass nhận về
@Composable
fun ForgotPasswordScreen(
    resultEmail: String?,
    resultOtp: String?,
    resultPassword: String?,
    onNext: (String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    val isValid = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()

    AuthScaffold(
        title = "Forget Password?",
        subtitle = "Enter your Email, we will send you a verification code."
    ) {
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Your Email",
            icon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            isError = email.isNotEmpty() && !isValid
        )
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Next", onClick = { onNext(email.trim()) }, enabled = isValid)

        if (resultEmail != null) {
            Spacer(Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SmartBlueLight, RoundedCornerShape(24.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                listOf(
                    "Email: $resultEmail",
                    "OTP: $resultOtp",
                    "Pass: $resultPassword"
                ).forEach {
                    Text(it, color = UthRed, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// Màn hình 2: nhập mã OTP gửi tới email
@Composable
fun VerificationScreen(email: String, onBack: () -> Unit, onNext: (String) -> Unit) {
    var otp by rememberSaveable { mutableStateOf("") }

    AuthScaffold(
        title = "Verify Code",
        subtitle = "Enter the code\nwe just sent you on your registered Email\n$email",
        onBack = onBack
    ) {
        OtpField(value = otp, onValueChange = { otp = it }, length = OTP_LENGTH)
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Next", onClick = { onNext(otp) }, enabled = otp.length == OTP_LENGTH)
    }
}

// Màn hình 3: tạo mật khẩu mới, hai ô phải trùng nhau
@Composable
fun ResetPasswordScreen(onBack: () -> Unit, onNext: (String) -> Unit) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }
    val mismatch = confirm.isNotEmpty() && confirm != password

    AuthScaffold(
        title = "Create new password",
        subtitle = "Your new password must be different form previously used password",
        onBack = onBack
    ) {
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            icon = Icons.Outlined.Lock,
            isPassword = true
        )
        Spacer(Modifier.height(16.dp))
        AuthTextField(
            value = confirm,
            onValueChange = { confirm = it },
            placeholder = "Confirm Password",
            icon = Icons.Outlined.Lock,
            isPassword = true,
            isError = mismatch
        )
        if (mismatch) {
            Text(
                "Passwords do not match",
                color = UthRed,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            )
        }
        Spacer(Modifier.height(24.dp))
        PrimaryButton(
            "Next",
            onClick = { onNext(password) },
            enabled = password.isNotEmpty() && password == confirm
        )
    }
}

// Màn hình 4: xem lại toàn bộ dữ liệu rồi submit về màn hình 1
@Composable
fun ConfirmScreen(
    email: String,
    otp: String,
    password: String,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    AuthScaffold(
        title = "Confirm",
        subtitle = "We are here to help you!",
        onBack = onBack
    ) {
        AuthTextField(email, {}, "Email", Icons.Outlined.Person, readOnly = true)
        Spacer(Modifier.height(16.dp))
        AuthTextField(otp, {}, "OTP", Icons.Outlined.Email, readOnly = true)
        Spacer(Modifier.height(16.dp))
        AuthTextField(password, {}, "Password", Icons.Outlined.Lock, isPassword = true, readOnly = true)
        Spacer(Modifier.height(24.dp))
        PrimaryButton("Summit", onClick = onSubmit)
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    MyApplicationTheme(darkTheme = false, dynamicColor = false) {
        ForgotPasswordScreen("uth@gmail.com", "12345", "secret", onNext = {})
    }
}

@Preview(showBackground = true)
@Composable
fun VerificationPreview() {
    MyApplicationTheme(darkTheme = false, dynamicColor = false) {
        VerificationScreen("uth@gmail.com", onBack = {}, onNext = {})
    }
}
