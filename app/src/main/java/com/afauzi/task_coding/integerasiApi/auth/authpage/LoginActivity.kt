package com.afauzi.task_coding.integerasiApi.auth.authpage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.afauzi.task_coding.MainActivity
import com.afauzi.task_coding.R
import com.afauzi.task_coding.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    // declaration for firebase authentication
    private lateinit var auth: FirebaseAuth

    private lateinit var createAccountInputArray: Array<EditText>

    private lateinit var signInEmail: String
    private lateinit var signInPassword: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initials auth
        auth = FirebaseAuth.getInstance()

        createAccountInputArray = arrayOf(binding.etemailSignIn, binding.etPasswordSignIn)

        actionToPage(binding.linkSignToSignup, SignUpActivity::class.java)

        binding.btnSignInToActionMain.setOnClickListener {
            signIn()
            closeKeyboard()
        }


        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Sign in with your account", Toast.LENGTH_SHORT).show()
        }

    }

    private fun signIn() {
        signInEmail = binding.etemailSignIn.text.toString().trim()
        signInPassword = binding.etPasswordSignIn.text.toString().trim()

        if (signInEmail.isNotEmpty() && signInPassword.isNotEmpty()) {
            auth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        actionToPageAndClearTask()
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Periksa kembali email & password \natau ada masalah jaringan",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } else if (signInPassword.trim().isEmpty() && signInEmail.trim().isEmpty()) {
            val snackbar = Snackbar.make(binding.root, "Column is required!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        } else if (signInEmail.trim().isEmpty()) {
            val snackbar = Snackbar.make(binding.root, "Email is required!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        } else {
            val snackbar = Snackbar.make(binding.root, "Password is required!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        }
    }

    private fun actionToPageAndClearTask() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun actionToPage(view: View, className: Class<*>) {
        view.setOnClickListener {
            startActivity(Intent(this, className))
            finish()
        }
    }

}