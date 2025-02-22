package views

import AppColors
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import classes.Order
import classes.Product
import fetchOrders
import kotlinx.coroutines.launch


@Composable
fun MainScreen(navController: NavHostController) {
    val orders = remember { mutableStateOf(emptyList<Order>()) }
    val coroutineScope = rememberCoroutineScope()
    val dialogState = remember { mutableStateOf(false) }
    val selectedProduct = remember { mutableStateOf(emptyList<Product>()) }
    val selectedPrice = remember { mutableStateOf(0.0) }
    val orderId = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            fetchOrders().collect { newOrder ->
                orders.value = newOrder

            }

        }
    }
    Scaffold(bottomBar = {
        BottomAppBar(backgroundColor = AppColors.colors.primaryColor) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(modifier = Modifier.indication(
                    interactionSource, null
                ), colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.colors.background, contentColor = Color.White
                ), onClick = {
                    navController.navigate("hist_screen")
                }) {
                    Text(text = "Geçmiş")
                }
            }
        }
    }) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize().paint(
                    painter = painterResource("background.jpg"),
                    contentScale = ContentScale.FillBounds
                )
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)
            ,

                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Card (modifier = Modifier.padding(innerPadding ).clip(RoundedCornerShape(9.dp)),backgroundColor = Color(0,255,234,
                    0x00000034
                )){
                    Column (modifier = Modifier.fillMaxSize()){
                        Row(
                            modifier = Modifier.fillMaxWidth().height(80.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.fillMaxHeight().width(150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Masa No",
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )

                            }
                            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                            Box(
                                modifier = Modifier.fillMaxHeight().width(150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "İsim",
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )

                            }
                            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                            Box(
                                modifier = Modifier.fillMaxHeight().width(150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Soyisim",
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )

                            }
                            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                            Box(
                                modifier = Modifier.fillMaxHeight().width(150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Onay",
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                        }
                        Divider(color = Color.White)
                        if (orders.value.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(color = Color.White)
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(orders.value.size) { order ->
                                    Row(
                                        Modifier.fillMaxWidth().height(80.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxHeight().width(150.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                orders.value[order].table,
                                                color = Color.White,
                                                fontSize = 30.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                        Box(
                                            modifier = Modifier.fillMaxHeight().width(150.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                orders.value[order].name,
                                                color = Color.White,
                                                fontSize = 25.sp
                                            )

                                        }
                                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                        Box(
                                            modifier = Modifier.fillMaxHeight().width(150.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                orders.value[order].surname,
                                                color = Color.White,
                                                fontSize = 25.sp
                                            )

                                        }

                                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                        Box(
                                            modifier = Modifier.fillMaxHeight().width(150.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (orders.value[order].confirm == "onaylanmadı") {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = null,
                                                    tint = Color.White
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = Color.White
                                                )
                                            }

                                        }
                                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                        Button(
                                            modifier = Modifier.indication(interactionSource, null),
                                            onClick = {
                                                val listProduct = orders.value[order].products
                                                orderId.value = orders.value[order].id
                                                selectedProduct.value = listProduct
                                                selectedPrice.value = orders.value[order].totalPrice
                                                dialogState.value = true
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                contentColor = Color.White,
                                                backgroundColor = AppColors.colors.primaryColor
                                            )
                                        ) {
                                            Text("Detaylar")
                                        }
                                    }
                                    Divider(color = Color.White)

                                }
                            }
                        }
                    }

                }

            }
            when {
                dialogState.value ->

                    ProductDetailDialog(
                        totalPrice = selectedPrice.value,
                        products = selectedProduct.value,
                        onDismissRequest = { dialogState.value = false },
                        orderId = orderId.value,
                        interactionSource = interactionSource
                    )

            }
        }
    }

}


