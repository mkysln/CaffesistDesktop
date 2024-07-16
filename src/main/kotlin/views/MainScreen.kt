package views

import AppColors
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import classes.firestore
import classes.getFirestoreInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun getDocumentField(collection: String,document: String, field: String): String?{
    return withContext(Dispatchers.IO){
        try {
            val db = getFirestoreInstance()
            val document = db?.collection(collection)?.document(document)?.get()?.get()
            document?.getString(field)
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val surname = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit){
        coroutineScope.launch {
            surname.value = getDocumentField("users","mkysln4@gmail.com","surname")
            println("fetched surname: ${surname.value}")
        }
    }
    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.colors.background) {
        Column {
            Column(modifier = Modifier.fillMaxWidth().height(70.dp), verticalArrangement = Arrangement.Center) {
                IconButton(modifier = Modifier, onClick = { navController.popBackStack()}){
                    Icon(modifier = Modifier.size(60.dp),imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = AppColors.colors.secondaryColor)
                }
            }

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                if (surname.value != null){
                    Text(text = surname.value!!, color = Color.White, fontSize = 30.sp)
                }else{
                    Text("Loading...", color = Color.White, fontSize = 30.sp)
                }
            }
        }

    }
}

@Preview
@Composable
fun MainPreview() {
    MainScreen(navController = rememberNavController())
}