package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.models.Curso
import com.example.models.DatabaseHelper
import com.example.models.Estudiante

class AddCurso : AppCompatActivity() {
    lateinit var personArg : Estudiante
    internal var databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_curso)

        personArg = intent.getSerializableExtra("Login") as Estudiante

        val agregar = findViewById<Button>(R.id.btnAddCurso)

        agregar.setOnClickListener{
            var editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcionAdd)
            var editTextEstudiante = findViewById<EditText>(R.id.editTextEstudianteAdd)
            var editTextCreditos = findViewById<EditText>(R.id.editTextCreditosAdd)

            var descripcion = editTextDescripcion.text.toString()
            var estudiante = Integer.parseInt(editTextEstudiante.text.toString())
            var creditos = Integer.parseInt(editTextCreditos.text.toString())

            val curso = Curso(0, descripcion, creditos, estudiante)
            databaseHelper.insertCurso(curso)
            val intent = Intent(this, CrudCursos::class.java)
            intent.putExtra("Login", personArg)
            startActivity(intent)
        }
    }
}