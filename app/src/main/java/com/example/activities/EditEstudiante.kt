package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.models.DatabaseEstudiante
import com.example.models.Estudiante

class EditEstudiante : AppCompatActivity() {
    lateinit var personArg : Estudiante
    internal var databaseEstudiante = DatabaseEstudiante(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_estudiante)

        personArg = intent.getSerializableExtra("Login") as Estudiante
        val estudiante = intent.getSerializableExtra("Estudiante") as Estudiante

        val actualizar = findViewById<Button>(R.id.button)

        actualizar.setOnClickListener{
            var nombre = findViewById<EditText>(R.id.editTextNombre)
            var apellido = findViewById<EditText>(R.id.editTextApellido)
            var edad = findViewById<EditText>(R.id.editTextEdad)

            val nuevoEstudiante = Estudiante(estudiante.id, nombre.text.toString(), estudiante.clave,
                apellido.text.toString(), Integer.parseInt(edad.text.toString()))

            databaseEstudiante.updateData(nuevoEstudiante)

            val intent = Intent(this, CrudEstudiantes::class.java)
            intent.putExtra("Login", personArg)
            startActivity(intent)
        }
    }
}
