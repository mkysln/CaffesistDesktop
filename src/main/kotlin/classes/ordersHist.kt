package classes

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import logErrorToFile

suspend fun HistOrder() = callbackFlow {
    val db = getFirestoreInstance()
    val listenerRegistration = db.collection("orders")
        .whereNotEqualTo("confirm","onaylanmadı")
        .whereEqualTo("payTo",true)
        .addSnapshotListener { snapshot, exception ->
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
            }
    awaitClose { listenerRegistration.remove() }
}


