package com.example.notes.utils

import com.google.firebase.auth.FirebaseAuth

class Constants {

    companion object{
        val CURRENT_USER= FirebaseAuth.getInstance().currentUser?.uid!!
    }
}