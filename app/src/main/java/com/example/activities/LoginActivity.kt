package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.models.Curso
import com.example.models.DatabaseHelper
import com.example.models.Estudiante

class LoginActivity : AppCompatActivity() {
    internal var databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.databaseHelper.insertEstudiante(Estudiante(123, "Juan", "123", "Sanchez", 60))
        //this.databaseHelper.insertCurso(Curso(0, "progra", 2, 123))

        var et_id = findViewById(R.id.et_id) as EditText
        var et_clave = findViewById(R.id.et_clave) as EditText
        var btn_submit = findViewById(R.id.btn_submit) as Button
        var btn_registrarse = findViewById(R.id.button2) as Button

        // set on-click listener
        btn_submit.setOnClickListener {
            val id = et_id.text
            val clave = et_clave.text
            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            if(databaseHelper.login(id.toString(), clave.toString()) != null){
                val bundle = Bundle()
                val Login = databaseHelper.login(id.toString(), clave.toString())
                val i = Intent(this, MainActivity::class.java)
                i.putExtra("Login", Login)
                startActivity(i)
                finish()
            }else{
                Toast.makeText(this, "El usuario no se encuentra registrado", Toast.LENGTH_SHORT).show()
            }

        }

        btn_registrarse.setOnClickListener{
            startActivity(Intent(this, AddEstudiante::class.java))
        }
    }
}