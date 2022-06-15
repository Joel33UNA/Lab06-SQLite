package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.models.DatabaseHelper
import com.example.models.Estudiante

class AddEstudiante : AppCompatActivity() {
    //lateinit var personArg : Estudiante
    internal var databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estudiante)

        //personArg = intent.getSerializableExtra("Login") as Estudiante

        val agregar = findViewById<Button>(R.id.btnAddCurso)

        agregar.setOnClickListener{
            var editTextNombre = findViewById<EditText>(R.id.editTextDescripcionAdd)
            var editTextApellido = findViewById<EditText>(R.id.editTextEstudianteAdd)
            var editTextCedula = findViewById<EditText>(R.id.editTextDescripcionAdd)
            var editTextClave = findViewById<EditText>(R.id.editTextClave)
            var editTextEdad = findViewById<EditText>(R.id.editTextCreditosAdd)

            var nombre = editTextNombre.text.toString()
            var apellido = editTextApellido.text.toString()
            var cedula = Integer.parseInt(editTextCedula.text.toString())
            var clave = editTextClave.text.toString()
            var edad = Integer.parseInt(editTextEdad.text.toString())

            val estudiante = Estudiante(cedula,
                nombre, clave, apellido, edad)
            databaseHelper.insertEstudiante(estudiante)
            val intent = Intent(this, LoginActivity::class.java)
            //intent.putExtra("Login", personArg)
            startActivity(intent)
        }
    }
}