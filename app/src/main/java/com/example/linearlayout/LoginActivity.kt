package com.example.linearlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.linearlayout.database.BancoDeDadosHelper
import com.example.linearlayout.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: BancoDeDadosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        database = BancoDeDadosHelper(this)
        database.readableDatabase

        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + binding.main.paddingStart,
                systemBars.top,
                systemBars.right + binding.main.paddingEnd,
                systemBars.bottom
            )
            insets
        }
        binding.buttonLogin.setOnClickListener(this)
        binding.buttonRegister.setOnClickListener(this)
    }

    override fun onClick(view: View){
        when(view.id){
            binding.buttonLogin.id -> { // ou R.id.buttonLogin
                val emailText: String = binding.edittextEmail.text.toString()
                val passwordText: String = binding.edittextPassword.text.toString()

                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(this, R.string.login_empty_field, Toast.LENGTH_SHORT).show()
                    return
                }
                if (!(emailText.contains("@") && emailText.contains("."))) {
                    Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show()
                    return
                }
                if (passwordText.length < 6) {
                    Toast.makeText(this, R.string.invalid_password, Toast.LENGTH_SHORT).show()
                    return
                }
                val authenticated = database.authenticateUser(emailText, passwordText)
                if (authenticated) {
                    Toast.makeText(this, R.string.success_login, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("EMAIL_KEY", emailText)
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, R.string.register_error, Toast.LENGTH_SHORT).show()
                }
            }
            binding.buttonRegister.id -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}