package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btnStart)
        val etname:EditText = findViewById(R.id.etName)

        btnStart.setOnClickListener {

            if(etname.text.isNotEmpty()){

                val intent = Intent(this,QuizQuestionActivity::class.java)

                intent.putExtra(Constants.USER_NAME,etname.text.toString())

                startActivity(intent)
                finish()

            }else{
                Toast.makeText(this, "Please enter your name ", Toast.LENGTH_SHORT).show()
            }

        }
    }
}