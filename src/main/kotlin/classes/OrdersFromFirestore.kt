package classes

import com.google.cloud.Timestamp

data class Order(
    val id: String = "",
    val confirm: String = "",
    val date: Timestamp? = null,
    val name: String = "",
    val payTo: Boolean = false,
    val products: List<Product> = emptyList(),
    val surname: String = "",
    val table: String = "",
    val totalPrice: Double = 0.0
)