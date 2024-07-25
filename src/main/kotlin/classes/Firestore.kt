package classes

import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient


fun getFirestoreInstance(): Firestore {
    return FirestoreClient.getFirestore()
}