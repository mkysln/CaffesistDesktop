package classes

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream

fun firestore() {
        try {
            val admin = FileInputStream("src/main/resources/adminSdk.json")
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(admin))
                .setDatabaseUrl("cafesist-3dc22.firebaseio.com/")
                .build()
            FirebaseApp.initializeApp(options)
            println("initialize succesfull")
        }catch (e:Exception){
            e.printStackTrace()
            println("initializing error:${e.message}")
        }
    }

fun getFirestoreInstance(): Firestore {
    return FirestoreClient.getFirestore()
}