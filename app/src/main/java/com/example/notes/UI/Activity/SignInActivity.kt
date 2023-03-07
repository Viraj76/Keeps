package com.example.notes.UI.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.R
import com.example.notes.databinding.ActivitySignInBinding
import com.example.notes.databinding.ItemBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this,SIgnUPActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            signedIn()
        }

    }

    private fun signedIn() {
        val email = binding.emailEt.text.toString()
        val password = binding.passET.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Signed In Successfully!",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            Toast.makeText(this,"Empty Fields are not Accepted",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}