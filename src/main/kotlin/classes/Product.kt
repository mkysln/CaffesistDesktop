package classes

import com.google.cloud.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Product(
    val name: String = "",
    val photo: String = "",
    val price: Double = 0.0,
    val type: String = "",
    val quantity: Int = 0
)
