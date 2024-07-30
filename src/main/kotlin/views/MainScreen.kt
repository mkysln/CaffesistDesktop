package views

import AppColors
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import classes.Order
import classes.Product
import classes.getFirestoreInstance
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

    LaunchedEffect(Unit){
        coroutineScope.launch {
            fetchOrders().collect{newOrder ->
                orders.value = newOrder

            }

        }
    }
    Scaffold(bottomBar = {
        BottomAppBar(backgroundColor = AppColors.colors.primaryColor) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.colors.background,
                    contentColor = Color.White
                ),onClick = {
                    navController.navigate("hist_screen")
                }){
                    Text(text = "Geçmiş")
                }
            }
        }
    }){
        Surface(modifier = Modifier.fillMaxSize(), color = AppColors.colors.background) {

            Column(modifier = Modifier.fillMaxSize().padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)) {
                Row(modifier = Modifier.fillMaxWidth().height(80.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.fillMaxHeight().width(150.dp), contentAlignment = Alignment.Center){
                        Text("Masa No", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)

                    }
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    Box(modifier = Modifier.fillMaxHeight().width(150.dp), contentAlignment = Alignment.Center){
                        Text("İsim", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)

                    }
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    Box(modifier = Modifier.fillMaxHeight().width(150.dp), contentAlignment = Alignment.Center){
                        Text("Soyisim", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)

                    }
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    Box(modifier = Modifier.fillMaxHeight().width(150.dp), contentAlignment = Alignment.Center){
                        Text("Onay", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    }

                }
                Divider(color = Color.White)
                if (orders.value.isEmpty()){
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }else{
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(orders.value.size){ order ->
                            Row(Modifier.fillMaxWidth().height(80.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.fillMaxHeight().width(150.dp), contentAlignment = Alignment.Center){
                                    Text(orders.value[order].table, color = Color.White, fontSize = 30.sp)
                                }
                                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                Box(modifier = Modifier.fillMaxHeight().width(150.dp), contentAlignment = Alignment.Center){
                                    Text(orders.value[order].name, color = Color.White, fontSize = 25.sp)

                                }
                                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                Box(modifier = Modifier.fillMaxHeight().width(150.dp), contentAlignment = Alignment.Center){
                                    Text(orders.value[order].surname, color = Color.White, fontSize = 25.sp)

                                }

                                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                Box(modifier = Modifier.fillMaxHeight().width(150.dp), contentAlignment = Alignment.Center){
                                    if (orders.value[order].confirm == "onaylanmadı"){
                                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White)
                                    }else{
                                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                                    }

                                }
                                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                Button(onClick = {
                                    val listProduct = orders.value[order].products
                                    orderId.value = orders.value[order].id
                                    selectedProduct.value = listProduct
                                    selectedPrice.value = orders.value[order].totalPrice
                                    dialogState.value = true
                                }, colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = AppColors.colors.primaryColor
                                )){
                                    Text("Detaylar")
                                }
                            }
                            Divider(color = Color.White)

                        }
                    }
                }
            }
            when{dialogState.value ->

                ProductDetailDialog(totalPrice = selectedPrice.value,products = selectedProduct.value, onDismissRequest = {dialogState.value = false}, orderId = orderId.value)

            }
        }
    }

}

@Composable
fun ProductDetailDialog(orderId: String,totalPrice: Double,products: List<Product>,onDismissRequest: () -> Unit) {
    val db = getFirestoreInstance()
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ){

        Surface(shape = MaterialTheme.shapes.medium,
            color = AppColors.colors.background,
            modifier = Modifier.padding(16.dp).height(700.dp).width(500.dp)) {
            Column(modifier = Modifier.fillMaxSize().padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("Siparişler", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("Sipariş no:  $orderId", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
                println("$products")
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.padding(vertical = 7.dp))
                    }
                    //ürün adı
                    items(products.size) { index ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
                                    Row {
                                        Text("${products[index].name} ", color = Color.White, fontSize = 20.sp)
                                        Text(text = "x ${products[index].quantity}",color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.padding(vertical = 5.dp))



                    }
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                    Column(modifier = Modifier.fillMaxWidth().height(80.dp), verticalArrangement = Arrangement.Bottom) {
                        Divider(color = Color.White)
                        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                            Text("Toplam Fiyat:${totalPrice}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd){
                                Row {
                                    Button(onClick = {
                                        db.collection("orders").document(orderId).update("confirm","onaylandı")
                                        onDismissRequest()
                                    }, colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.White,
                                        backgroundColor = AppColors.colors.primaryColor
                                    )){
                                        Text("Onayla", color = Color.White)
                                    }
                                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                                    Button(onClick = {
                                        db.collection("orders").document(orderId).update("confirm","iptal")
                                        onDismissRequest()}, colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.White,
                                        backgroundColor = AppColors.colors.primaryColor
                                    )){
                                        Text("Reddet", color = Color.White)
                                    }
                                }

                            }

                        }
                    }
                }


            }
        }

    }
}
