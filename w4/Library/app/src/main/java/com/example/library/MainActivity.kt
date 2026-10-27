package com.example.library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.library.ui.theme.LibBlue
import com.example.library.ui.theme.LibGreyText
import com.example.library.ui.theme.LibraryTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryTheme(darkTheme = false, dynamicColor = false) {
                LibraryApp()
            }
        }
    }
}

@Serializable
sealed class Tab {
    @Serializable
    data object Manage : Tab()

    @Serializable
    data object Books : Tab()

    @Serializable
    data object Students : Tab()
}

private data class TabItem(val route: Tab, val label: String, val icon: ImageVector)

private val tabs = listOf(
    TabItem(Tab.Manage, "Quản lý", Icons.Filled.Home),
    TabItem(Tab.Books, "DS Sách", Icons.Filled.LibraryBooks),
    TabItem(Tab.Students, "Sinh viên", Icons.Filled.Person)
)

@Composable
fun LibraryApp(viewModel: LibraryViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                tabs.forEach { tab ->
                    val selected = backStackEntry?.destination?.hasRoute(tab.route::class) == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = LibBlue,
                            selectedTextColor = LibBlue,
                            unselectedIconColor = LibGreyText,
                            unselectedTextColor = LibGreyText,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Tab.Manage,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Tab.Manage> { ManageScreen(viewModel) }
            composable<Tab.Books> { BooksScreen(viewModel) }
            composable<Tab.Students> { StudentsScreen(viewModel) }
        }
    }
}
