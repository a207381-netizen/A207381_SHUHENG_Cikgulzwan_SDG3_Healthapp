package com.example.a207381_cikguizwan_lab01

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Locale

class FirestoreRepository {

    private val collectionName = "health_records"

    fun saveHealthMessage(
        message: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val database =
            try {
                FirebaseFirestore.getInstance()
            } catch (exception: Exception) {
                onError(exception.message ?: "Firebase is not configured.")
                return
            }

        val record =
            hashMapOf(
                "message" to message,
                "timestamp" to FieldValue.serverTimestamp()
            )

        database
            .collection(collectionName)
            .add(record)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Failed to save record.")
            }
    }

    fun listenToHealthRecords(
        onRecordsChanged: (List<FirestoreRecord>) -> Unit,
        onError: (String) -> Unit
    ): ListenerRegistration {
        val database =
            try {
                FirebaseFirestore.getInstance()
            } catch (exception: Exception) {
                onError(exception.message ?: "Firebase is not configured.")
                return ListenerRegistration {
                }
            }

        return database
            .collection(collectionName)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    onError(exception.message ?: "Failed to load cloud records.")
                    return@addSnapshotListener
                }

                val records =
                    snapshot
                        ?.documents
                        ?.map { document ->
                            val timestamp =
                                document.getTimestamp("timestamp")

                            FirestoreRecord(
                                id = document.id,
                                message = document.getString("message") ?: "",
                                timestamp = formatTimestamp(timestamp)
                            )
                        }
                        ?: emptyList()

                onRecordsChanged(records)
            }
    }

    private fun formatTimestamp(
        timestamp: Timestamp?
    ): String {
        if (timestamp == null) {
            return "Pending"
        }

        val formatter =
            SimpleDateFormat(
                "dd MMM yyyy, HH:mm",
                Locale.getDefault()
            )

        return formatter.format(timestamp.toDate())
    }
}
