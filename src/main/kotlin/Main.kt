import Firebase.initializeFirebase
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import classes.Colors
import views.Login
import views.MainScreen
import views.Register

object AppColors{
    val colors = Colors()
}

@Composable
fun App() {
    initializeFirebase()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_screen"){
        composable("main_screen"){
            MainScreen(navController = navController)
        }
        composable("login_screen"){
            Login(navController = navController)
        }
        composable("register_screen"){
            Register(navController = navController)
        }
    }
}

fun main() = application {

    Window(onCloseRequest = ::exitApplication, title = "Ceffesist",
        icon = painterResource("caffesist.png"), state = WindowState(placement = WindowPlacement.Maximized) ) {
        App()
    }
}
