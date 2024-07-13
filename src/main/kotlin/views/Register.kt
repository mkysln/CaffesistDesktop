package views

import AppColors
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun Register(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize(), color = AppColors.colors.background) {


        Column {
            IconButton(onClick = {navController.popBackStack()}){
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "", tint = AppColors.colors.primaryColor)
            }


        }
    }
}