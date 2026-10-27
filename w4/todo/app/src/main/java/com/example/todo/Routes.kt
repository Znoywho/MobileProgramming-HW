package com.example.todo

import androidx.compose.runtime.Composable
import com.example.todo.ui.components.CategoryIllustration
import com.example.todo.ui.components.ReminderIllustration
import com.example.todo.ui.components.TaskListIllustration
import kotlinx.serialization.Serializable


@Serializable
sealed class Destination(val label: String) {
    @Serializable
    data object Splash : Destination("Splash")


    @Serializable
    data object Onboarding1 : Destination("Onboarding 1")


    @Serializable
    data object Onboarding2 : Destination("Onboarding 2")


    @Serializable
    data object Onboarding3 : Destination("Onboarding 3")


    @Serializable
    data object Home : Destination("Home")


}


sealed class OnboardingPage(
    val index: Int,
    val title: String,
    val description: String,
    val buttonText: String,
    val route: Destination,
    val next: Destination,
    val illustration: @Composable () -> Unit
) {
    data object Step1 : OnboardingPage(
        0,
        "Mọi việc, gọn trong một chỗ",
        "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.",
        "Tiếp tục",
        Destination.Onboarding1,
        Destination.Onboarding2,
        { TaskListIllustration() }
    )

    data object Step2 : OnboardingPage(
        1,
        "Chia theo Học tập và Cuộc sống",
        "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì.",
        "Tiếp tục",
        Destination.Onboarding2,
        Destination.Onboarding3,
        { CategoryIllustration() }
    )

    data object Step3 : OnboardingPage(
        2,
        "Nhắc đúng lúc",
        "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.",
        "Bắt đầu ngay",
        Destination.Onboarding3,
        Destination.Home,
        { ReminderIllustration() }
    )

    companion object {
        val all: List<OnboardingPage> by lazy { listOf(Step1, Step2, Step3) }
    }
}
