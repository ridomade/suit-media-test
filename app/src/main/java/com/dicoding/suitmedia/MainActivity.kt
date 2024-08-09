package com.dicoding.suitmedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.toolbar.setNavigationOnClickListener {

            finish()
        }
        val name = intent.getStringExtra("EXTRA_NAME")
        binding.tvName.text = name

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val name = data?.getStringExtra("SELECTED_USER_NAME")
            binding.tvSelected.text = name ?: "No user selected"
        }
    }
}
