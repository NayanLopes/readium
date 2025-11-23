package com.example.readium.repository

import com.example.readium.firebase.FirebaseConfig
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    
    private val auth = FirebaseConfig.auth
    private val firestore = FirebaseConfig.firestore
    
    // Autenticação
    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createUserWithEmail(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun signOut() {
        auth.signOut()
    }
    
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
    
    // Firestore - Livros
    suspend fun saveBook(book: Map<String, Any>): Result<String> {
        return try {
            val docRef = firestore.collection("books").document()
            docRef.set(book).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getBooks(): Result<List<DocumentSnapshot>> {
        return try {
            val snapshot = firestore.collection("books").get().await()
            Result.success(snapshot.documents)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserBooks(userId: String): Result<List<DocumentSnapshot>> {
        return try {
            val snapshot = firestore.collection("books")
                .whereEqualTo("userId", userId)
                .get()
                .await()
            Result.success(snapshot.documents)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
