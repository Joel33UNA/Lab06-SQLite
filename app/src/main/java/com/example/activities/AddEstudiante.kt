package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.models.DatabaseEstudiante
import com.example.models.Estudiante

class AddEstudiante : AppCompatActivity() {
    lateinit var personArg : Estudiante
    internal var databaseEstudiante = DatabaseEstudiante(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estudiante)

        personArg = intent.getSerializableExtra("Login") as Estudiante

        val agregar = findViewById<Button>(R.id.btnAgregar)

        agregar.setOnClickListener{
            var editTextNombre = findViewById<EditText>(R.id.editTextNombreAdd)
            var editTextApellido = findViewById<EditText>(R.id.editTextApellidoAdd)
            var editTextCedula = findViewById<EditText>(R.id.editTextNombreAdd)
            var editTextClave = findViewById<EditText>(R.id.editTextClave)
            var editTextEdad = findViewById<EditText>(R.id.editTextEdadAdd)

            var nombre = editTextNombre.text.toString()
            var apellido = editTextApellido.text.toString()
            var cedula = Integer.parseInt(editTextCedula.text.toString())
            var clave = editTextClave.text.toString()
            var edad = Integer.parseInt(editTextEdad.text.toString())

            val estudiante = Estudiante(cedula,
                nombre, clave, apellido, edad)
            databaseEstudiante.insertData(estudiante)
            val intent = Intent(this, CrudEstudiantes::class.java)
            intent.putExtra("Login", personArg)
            startActivity(intent)
        }
    }
}