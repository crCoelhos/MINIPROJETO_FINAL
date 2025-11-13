package com.example.linearlayout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.linearlayout.R
import com.example.linearlayout.database.BancoDeDadosHelper
import com.example.linearlayout.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: BancoDeDadosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.main)
        database = BancoDeDadosHelper(this)

        binding.buttonRegisterConfirm.setOnClickListener {
            val email = binding.edittextEmail.text.toString().trim()
            val password = binding.edittextPassword.text.toString()
            val termsAccepted = binding.checkboxTerms.isChecked

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.login_empty_field), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!(email.contains("@") && email.contains("."))) {
                Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!termsAccepted) {
                Toast.makeText(this, getString(R.string.accept_terms), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = database.insertUser(email, password)
            if (result != -1L) {
                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.register_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}