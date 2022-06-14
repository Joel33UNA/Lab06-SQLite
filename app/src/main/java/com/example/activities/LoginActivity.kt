package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.models.DatabaseEstudiante
import com.example.models.Estudiante

class LoginActivity : AppCompatActivity() {
    internal var databaseEstudiante = DatabaseEstudiante(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //this.databaseEstudiante.insertData(Estudiante(123, "Juan", "123", "Sanchez", 60))

        var et_id = findViewById(R.id.et_id) as EditText
        var et_clave = findViewById(R.id.et_clave) as EditText
        var btn_submit = findViewById(R.id.btn_submit) as Button

        // set on-click listener
        btn_submit.setOnClickListener {
            val id = et_id.text
            val clave = et_clave.text
            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            if(databaseEstudiante.login(id.toString(), clave.toString()) != null){
                val bundle = Bundle()
                val Login = databaseEstudiante.login(id.toString(), clave.toString())
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("Login", Login)
                startActivity(i)
                finish()
            }else{
                Toast.makeText(this, "El usuario no se encuentra registrado", Toast.LENGTH_SHORT).show()
            }

        }
    }
}