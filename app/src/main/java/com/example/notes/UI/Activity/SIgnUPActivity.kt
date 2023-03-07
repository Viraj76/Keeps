package com.example.notes.UI.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes.R
import com.example.notes.databinding.ActivitySignUpactivityBinding
import com.google.firebase.auth.FirebaseAuth

class SIgnUPActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpactivityBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener { signedUpUser() }
        binding.tvSignInOption.setOnClickListener { goingToSignInActivity() }


    }

    private fun signedUpUser() {
        val email = binding.emailEt.text.toString()
        val password = binding.passET.text.toString()
        val confirmPassword = binding.confirmPassEt.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
            if(password == confirmPassword){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this,SignInActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this,"Signed Up Successfully!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        else{
            Toast.makeText(this,"Empty Fields are not Accepted",Toast.LENGTH_SHORT).show()
        }
    }

    private fun goingToSignInActivity() {
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
    }
}