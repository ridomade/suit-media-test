package com.dicoding.suitmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.suitmedia.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnCheck.setOnClickListener {
            val palindromText = binding.etPalindrom.text.toString()
            val isPalindrome = isPalindrome(palindromText)
            val message = if (isPalindrome) {
                "The text is a palindrome."
            } else {
                "The text is not a palindrome."
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


        binding.btnNext.setOnClickListener {

            val name = binding.etName.text.toString()


            if (name.isBlank()) {
                Toast.makeText(this, "Name is required.", Toast.LENGTH_SHORT).show()
            } else {

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("EXTRA_NAME", name)
                startActivity(intent)
            }
        }
    }


    private fun isPalindrome(text: String): Boolean {
        val cleanedText = text.replace(" ", "").lowercase()
        return cleanedText == cleanedText.reversed()
    }
}
