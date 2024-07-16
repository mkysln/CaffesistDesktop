package views

import AppColors
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import classes.Colors
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState){ snackbarData ->
        Snackbar(snackbarData = snackbarData, contentColor = Color.White, backgroundColor = Color.Red, actionColor = Color.White)
    } } ){
        Surface(modifier = Modifier.fillMaxSize(), color = AppColors.colors.background){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(value = email.value, onValueChange = {
                    email.value = it
                }, label = {
                    Text("Email")
                },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedLabelColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        textColor = AppColors.colors.secondaryColor,
                        focusedLabelColor = AppColors.colors.primaryColor,
                        focusedBorderColor = AppColors.colors.primaryColor,
                        disabledTextColor = Color.LightGray,
                        cursorColor = AppColors.colors.primaryColor
                    )
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                OutlinedTextField(value = password.value, onValueChange = {
                    password.value = it
                }, label = {
                    Text("Parola")
                },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    ,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedLabelColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        textColor = AppColors.colors.secondaryColor,
                        focusedLabelColor = AppColors.colors.primaryColor,
                        focusedBorderColor = AppColors.colors.primaryColor,
                        disabledTextColor = Color.LightGray,
                        cursorColor = AppColors.colors.primaryColor
                    )
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Button(onClick = {
                    /*
                    if(email.value.isNotEmpty() && password.value.isNotEmpty()){

                    }else{
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Lütfen Gerekli Alanları Doldurun",
                                duration = SnackbarDuration.Short,
                                actionLabel = "Kapat"
                            )
                        }
                    }*/





                   navController.navigate("main_screen")
                },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = AppColors.colors.secondaryColor,
                        backgroundColor = AppColors.colors.primaryColor
                    )){
                    Text("Giriş Yap")
                }
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                TextButton(onClick = {navController.navigate("register_screen")}){
                    Text(text = "Kaydol", color = AppColors.colors.primaryColor)
                }
            }
        }
    }

}