import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import classes.Colors
import classes.firestore
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreBundle
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import views.Login
import views.MainScreen
import views.Register
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
}
