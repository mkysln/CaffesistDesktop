import classes.Order
import classes.Product
import classes.getFirestoreInstance
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

fun fetchOrders() = callbackFlow {
    val db = getFirestoreInstance()
    val listenerRegistration = db.collection("orders")
        .whereEqualTo("confirm","onaylanmadı")
        .whereEqualTo("payTo",true)
        .addSnapshotListener { snapshot, exception ->
            try {
                if (exception != null) {
                    logErrorToFile(exception)
                    close(exception)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val orders = snapshot.documents.map { document ->
                        val productMaps = document.get("products") as? List<Map<String, Any>>
                        val products = productMaps?.map { productMap ->
                            val productDetails = productMap["product"] as? Map<String, Any>
                            Product(
                                name = productDetails?.get("name") as? String ?: "",
                                photo = productDetails?.get("photo") as? String ?: "",
                                price = (productDetails?.get("price") as? Number)?.toDouble() ?: 0.0,
                                type = productDetails?.get("type") as? String ?: "",
                                quantity = (productMap["quantity"] as? Number)?.toInt() ?: 0
                            )
                        } ?: emptyList()

                        Order(
                            id = document.id,
                            confirm = document.getString("confirm") ?: "",
                            date = document.getTimestamp("date"),
                            name = document.getString("name") ?: "",
                            payTo = document.getBoolean("payTo") ?: false,
                            products = products,
                            surname = document.getString("surname") ?: "",
                            table = document.getString("table") ?: "",
                            totalPrice = (document.get("totalPrice") as? Number)?.toDouble() ?: 0.0
                        )
                    }

                    if (snapshot.isEmpty) {
                        // Handle empty snapshot (e.g., emit empty list or signal)
                    } else {
                        trySend(orders)
                    }
                }
            } catch (e: Exception){
                logErrorToFile(e)
                close(e)
            }


        }
    awaitClose { listenerRegistration.remove() }
}

fun logErrorToFile(exception: Exception) {
    val logFile = getLogFile()
    logFile.appendText("${Date()}: ${exception.message}\n")
    val sw = StringWriter()
    exception.printStackTrace(PrintWriter(sw))
    logFile.appendText("${sw}\n")
}

fun getLogFile(): File {
    val logDir = File("C:/MyAppLogs")
    if (!logDir.exists()) {
        logDir.mkdir()
    }
    return File(logDir, "error_log.txt")
}