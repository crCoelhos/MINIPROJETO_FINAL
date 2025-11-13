package com.example.linearlayout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.linearlayout.database.BancoDeDadosHelper
import com.example.linearlayout.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var database: BancoDeDadosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.main)
        database = BancoDeDadosHelper(this)

        val emailText = intent.getStringExtra("EMAIL_KEY")

        if (emailText != null) {
            binding.textEmail.text = emailText
        } else {
            Toast.makeText(this, "Erro ao receber dados", Toast.LENGTH_SHORT).show()
        }

        binding.buttonDelete.setOnClickListener {
            val email = emailText
            if (email != null) {
                val rows = database.deleteUserByEmail(email)
                if (rows > 0) {
                    Toast.makeText(this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.delete_error), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.delete_error), Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}