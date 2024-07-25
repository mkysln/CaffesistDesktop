import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import classes.Colors
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import views.HistScreen
import views.MainScreen
import java.io.FileInputStream

object AppColors{
    val colors = Colors()
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Ceffesist",
        icon = painterResource("caffesist.png"), state = WindowState(placement = WindowPlacement.Maximized) ) {
        val admin = FileInputStream("src/main/resources/adminSdk.json")
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(admin))
            .setDatabaseUrl("cafesist-3dc22.firebaseio.com/")
            .build()
        FirebaseApp.initializeApp(options)


        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "main_screen"){
            composable("main_screen"){
                MainScreen(navController = navController)
            }
            composable("hist_screen"){
                HistScreen(navController = navController)
            }
        }

    }
}
