package views

import AppColors
import Firebase.getDocumentFromFirestore
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson

data class FirestoreDocument(
    val name: String,
    val fields: Map<String, Any>,
    val createTime: String,
    val updateTime: String
)


@Composable
fun MainScreen(navController: NavHostController) {
    var documentData by remember { mutableStateOf<String?>(null) }
    val docValueIsField = remember { mutableStateOf("") }
    LaunchedEffect(true) {
        val collectionName = "deneme"
        val documentName = "desktop"
        documentData = getDocumentFromFirestore(collectionName, documentName)
        if (documentData != null) {
            println("documentData: $documentData")
            val firestoreDocument = Gson().fromJson(documentData,FirestoreDocument:: class.java)
            docValueIsField.value = (firestoreDocument.fields["a"]as? Map<*, *>)?.get("stringValue").toString()
        } else {
            println("documentData: null")
        }
    }
    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.colors.background) {
        Column {
            IconButton(onClick = { navController.popBackStack()}){
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = AppColors.colors.primaryColor)
            }

            Text(text = docValueIsField.value, fontSize = 35.sp, color = AppColors.colors.secondaryColor)

        }
    }
}
