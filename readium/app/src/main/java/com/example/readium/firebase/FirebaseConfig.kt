package com.example.readium.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context

object FirebaseConfig {
    private var _auth: FirebaseAuth? = null
    private var _firestore: FirebaseFirestore? = null
    
    val auth: FirebaseAuth
        get() = _auth ?: throw IllegalStateException("Firebase não foi inicializado")
    
    val firestore: FirebaseFirestore
        get() = _firestore ?: throw IllegalStateException("Firebase não foi inicializado")
    
    fun initialize(context: Context) {
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context)
        }
        
        _auth = FirebaseAuth.getInstance()
        _firestore = FirebaseFirestore.getInstance()
    }
    
    fun isInitialized(): Boolean {
        return _auth != null && _firestore != null
    }
}
