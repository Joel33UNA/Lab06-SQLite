package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.models.Curso
import com.example.models.DatabaseHelper
import com.example.models.Estudiante

class EditCurso : AppCompatActivity() {
    lateinit var personArg : Estudiante
    internal var databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_curso)

        personArg = intent.getSerializableExtra("Login") as Estudiante
        val curso = intent.getSerializableExtra("Curso") as Curso

        val actualizar = findViewById<Button>(R.id.btnAddCurso)

        actualizar.setOnClickListener{
            var editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcionEdit)
            var editTextCreditos = findViewById<EditText>(R.id.editTextCreditosEdit)

            var descripcion = editTextDescripcion.text.toString()
            var creditos = Integer.parseInt(editTextCreditos.text.toString())

            val nuevoCurso = Curso(curso.id, descripcion, creditos, curso.idEstudiante)

            databaseHelper.updateCurso(nuevoCurso)

            val intent = Intent(this, CrudCursos::class.java)
            intent.putExtra("Login", personArg)
            startActivity(intent)
        }
    }
}