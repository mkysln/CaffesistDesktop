package Firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


val firebaseConfig = mapOf(
    "apiKey" to "AIzaSyBA3baYnstmjjwRhubPSWKO6fxnvGptNjU",
    "authDomain" to "kluto-fd2de.firebaseapp.com",
    "projectId" to "kluto-fd2de",
    "storageBucket" to "kluto-fd2de.appspot.com",
    "messagingSenderId" to "354318208196",
    "appId" to "1:354318208196:web:715d9f549a79f342f2cd91",
    "measurementId" to "G-X5VWWCB0GC"
)

fun initializeFirebase() {
    val serviceAccount = FileInputStream("src/main/resources/adminSdk.json")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    FirebaseApp.initializeApp(options)
}



fun getDocumentFromFirestore(collection: String, document: String): String? {
    val apiKey = firebaseConfig["apiKey"]
    val projectId = firebaseConfig["kluto-fd2de"]
    val url = URL("https://firestore.googleapis.com/v1/projects/${firebaseConfig["projectId"]}/databases/(default)/documents/$collection/$document?key=$apiKey")
    val conn = url.openConnection() as HttpURLConnection
    conn.requestMethod = "GET"
    conn.setRequestProperty("Content-Type", "application/json")

    val responseCode = conn.responseCode
    println("GET Response Code :: $responseCode")
    return if (responseCode == HttpURLConnection.HTTP_OK) {
        conn.inputStream.bufferedReader().use { it.readText() }
    } else {
        null
    }
}


fun signInWithEmailAndPassword(email: String, password: String): String? {
    val apiKey = firebaseConfig["apiKey"]
    val url = URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$apiKey")
    val conn = url.openConnection() as HttpURLConnection
    conn.requestMethod = "POST"
    conn.setRequestProperty("Content-Type", "application/json")
    conn.doOutput = true

    val jsonInputString = """{"email":"$email", "password":"$password", "returnSecureToken":true}"""
    val out = conn.outputStream
    out.write(jsonInputString.toByteArray(StandardCharsets.UTF_8))
    out.flush()
    out.close()

    val responseCode = conn.responseCode
    println("POST Response Code :: $responseCode")
    return if (responseCode == HttpURLConnection.HTTP_OK) {
        conn.inputStream.bufferedReader().use { it.readText() }
    } else {
        null
    }
}

