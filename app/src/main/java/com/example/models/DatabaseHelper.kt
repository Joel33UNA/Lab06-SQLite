package com.example.models

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

/**
 * Let's start by creating our database CRUD helper class
 * based on the SQLiteHelper.
 */
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    /**
     * Our onCreate() method.
     * Called when the database is created for the first time. This is
     * where the creation of tables and the initial population of the tables
     * should happen.
     */
    override fun onCreate(db: SQLiteDatabase) {
        var sql1 = "CREATE TABLE $TABLE_ESTUDIANTE (ID INTEGER PRIMARY KEY,CLAVE TEXT,NOMBRE TEXT,APELLIDO TEXT,EDAD INTEGER)"
        db.execSQL(sql1)

        var sql2 = "CREATE TABLE $TABLE_CURSO(" +
                "COD INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DESCRIPCION TEXT," +
                "CREDITOS INTEGER," +
                "ID_ESTUDIANTE INTEGER," +
                "FOREIGN KEY (ID_ESTUDIANTE) REFERENCES Estudiante(ID))"
        db.execSQL(sql2)
    }

    /**
     * Let's create Our onUpgrade method
     * Called when the database needs to be upgraded. The implementation should
     * use this method to drop tables, add tables, or do anything else it needs
     * to upgrade to the new schema version.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTUDIANTE)
        onCreate(db)

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURSO)
        onCreate(db)
    }

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    @Throws(SQLiteConstraintException::class)
    fun insertEstudiante(estudiante:Estudiante) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, estudiante.id)
        contentValues.put(NOMBRE, estudiante.nombre)
        contentValues.put(CLAVE, estudiante.clave)
        contentValues.put(APELLIDO, estudiante.apellido)
        contentValues.put(EDAD, estudiante.edad)
        db.insert(TABLE_ESTUDIANTE, null, contentValues)
    }

    /**
     * Let's create  a method to update a row with new field values.
     */
    fun updateEstudiante(estudiante:Estudiante):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, estudiante.id)
        contentValues.put(NOMBRE, estudiante.nombre)
        contentValues.put(CLAVE, estudiante.clave)
        contentValues.put(APELLIDO, estudiante.apellido)
        contentValues.put(EDAD, estudiante.edad)
        db.update(TABLE_ESTUDIANTE, contentValues, "ID = ?", arrayOf(estudiante.id.toString()))
        return true
    }

    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteEstudiante(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_ESTUDIANTE,"ID = ?", arrayOf(id))
    }

    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    @SuppressLint("Range")
    fun readEstudiantes(): ArrayList<Estudiante> {
        val estudiantes = ArrayList<Estudiante>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_ESTUDIANTE", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id: Int
        var nombre: String
        var clave: String
        var apellido: String
        var edad: Int
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
                clave = cursor.getString(cursor.getColumnIndex(CLAVE))
                apellido = cursor.getString(cursor.getColumnIndex(APELLIDO))
                edad = cursor.getInt(cursor.getColumnIndex(EDAD))

                estudiantes.add(Estudiante(id, nombre, clave, apellido, edad))
                cursor.moveToNext()
            }
        }
        return estudiantes
    }

    @SuppressLint("Range")
    fun readEstudiante(id: String): ArrayList<Estudiante> {
        val estudiantes = ArrayList<Estudiante>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_ESTUDIANTE WHERE $ID = $id", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id: Int
        var nombre: String
        var clave: String
        var apellido: String
        var edad: Int
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
                clave = cursor.getString(cursor.getColumnIndex(CLAVE))
                apellido = cursor.getString(cursor.getColumnIndex(APELLIDO))
                edad = cursor.getInt(cursor.getColumnIndex(EDAD))

                estudiantes.add(Estudiante(id, nombre, clave, apellido, edad))
                cursor.moveToNext()
            }
        }
        return estudiantes
    }

    @SuppressLint("Range")
    fun login(id:String, clave:String) : Estudiante?{
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_ESTUDIANTE WHERE $ID = $id AND $CLAVE = $clave", null)
        } catch (e: SQLiteException) {
            return null
        }
        if (cursor!!.moveToNext()) {
            var id = cursor.getInt(cursor.getColumnIndex(ID))
            var nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
            var clave = cursor.getString(cursor.getColumnIndex(CLAVE))
            var apellido = cursor.getString(cursor.getColumnIndex(APELLIDO))
            var edad = cursor.getInt(cursor.getColumnIndex(EDAD))
            return Estudiante(id, nombre, clave, apellido, edad)
        }
        else return null
    }


    @Throws(SQLiteConstraintException::class)
    fun insertCurso(curso:Curso) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DESCRIPCION, curso.descripcion)
        contentValues.put(CREDITOS, curso.creditos)
        contentValues.put(ID_ESTUDIANTE, curso.idEstudiante)
        db.insert(TABLE_CURSO, null, contentValues)
    }

    fun updateCurso(curso:Curso):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DESCRIPCION, curso.descripcion)
        contentValues.put(CREDITOS, curso.creditos)
        contentValues.put(ID_ESTUDIANTE, curso.idEstudiante)
        db.update(TABLE_CURSO, contentValues, "COD = ?", arrayOf(curso.id.toString()))
        return true
    }

    fun deleteCurso(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_CURSO,"COD = ?", arrayOf(id))
    }

    @SuppressLint("Range")
    fun readCursos(): ArrayList<Curso> {
        val cursos = ArrayList<Curso>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_CURSO", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id = 0
        var descripcion = ""
        var creditos = 0
        var idEstudiante = 0
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(COD))
                descripcion = cursor.getString(cursor.getColumnIndex(DESCRIPCION))
                creditos = cursor.getInt(cursor.getColumnIndex(CREDITOS))
                idEstudiante = cursor.getInt(cursor.getColumnIndex(ID_ESTUDIANTE))

                cursos.add(Curso(id, descripcion, creditos, idEstudiante))
                cursor.moveToNext()
            }
        }
        return cursos
    }

    @SuppressLint("Range")
    fun readCursosEst(idEs: Int): ArrayList<Curso> {
        val cursos = ArrayList<Curso>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_CURSO where $idEs = ID_ESTUDIANTE", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id = 0
        var descripcion = ""
        var creditos = 0
        var idEstudiante = 0
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(COD))
                descripcion = cursor.getString(cursor.getColumnIndex(DESCRIPCION))
                creditos = cursor.getInt(cursor.getColumnIndex(CREDITOS))
                idEstudiante = cursor.getInt(cursor.getColumnIndex(ID_ESTUDIANTE))

                cursos.add(Curso(id, descripcion, creditos, idEstudiante))
                cursor.moveToNext()
            }
        }
        return cursos
    }

    @SuppressLint("Range")
    fun readCurso(id: String): ArrayList<Curso> {
        val cursos = ArrayList<Curso>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_CURSO WHERE $COD = $id", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id = 0
        var descripcion = ""
        var creditos = 0
        var idEstudiante = 0
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(COD))
                descripcion = cursor.getString(cursor.getColumnIndex(DESCRIPCION))
                creditos = cursor.getInt(cursor.getColumnIndex(CREDITOS))
                idEstudiante = cursor.getInt(cursor.getColumnIndex(ID_ESTUDIANTE))

                cursos.add(Curso(id, descripcion, creditos, idEstudiante))
                cursor.moveToNext()
            }
        }
        return cursos
    }

    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */
    companion object {
        val DATABASE_NAME = "SQLite"
        
        val TABLE_ESTUDIANTE = "Estudiante"
        val ID = "ID"
        val NOMBRE = "NOMBRE"
        val CLAVE = "CLAVE"
        val APELLIDO = "APELLIDO"
        val EDAD = "EDAD"

        val TABLE_CURSO = "Curso"
        val COD = "COD"
        val DESCRIPCION = "DESCRIPCION"
        val CREDITOS = "CREDITOS"
        val ID_ESTUDIANTE = "ID_ESTUDIANTE"
    }
}

//end
