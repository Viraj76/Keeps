package com.example.notes.utils

import com.google.firebase.auth.FirebaseAuth

object Config {
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
}