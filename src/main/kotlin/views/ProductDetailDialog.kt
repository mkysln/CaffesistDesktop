package views

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import classes.Product
import classes.getFirestoreInstance

@Composable
fun ProductDetailDialog(
    orderId: String, totalPrice: Double,
    products: List<Product>,
    onDismissRequest: () -> Unit,
    interactionSource: InteractionSource
) {
    val db = getFirestoreInstance()
    Dialog(onDismissRequest = {
        onDismissRequest()
    }) {

        Surface(
            shape = MaterialTheme.shapes.medium,
            color = AppColors.colors.background,
            modifier = Modifier.padding(16.dp).height(700.dp).width(500.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Siparişler",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Sipariş no:  $orderId",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.padding(vertical = 7.dp))
                    }
                    items(products.size) { index ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row {
                                    Text(
                                        "${products[index].name} ",
                                        color = Color.White,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "x ${products[index].quantity}",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                            }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))


                    }
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    Column(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Divider(color = Color.White)
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Toplam Fiyat:${totalPrice}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Row {
                                    Button(
                                        modifier = Modifier.indication(interactionSource,null),
                                        onClick = {
                                            db.collection("orders").document(orderId)
                                                .update("confirm", "onaylandı")
                                            onDismissRequest()
                                        }, colors = ButtonDefaults.buttonColors(
                                            contentColor = Color.White,
                                            backgroundColor = AppColors.colors.primaryColor
                                        )
                                    ) {
                                        Text("Onayla", color = Color.White)
                                    }
                                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                                    Button(modifier = Modifier.indication(interactionSource,null),
                                        onClick = {
                                            db.collection("orders").document(orderId)
                                                .update("confirm", "iptal")
                                            onDismissRequest()
                                        }, colors = ButtonDefaults.buttonColors(
                                            contentColor = Color.White,
                                            backgroundColor = AppColors.colors.primaryColor
                                        )
                                    ) {
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