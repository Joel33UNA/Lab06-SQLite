package com.example.models

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseCurso(context : Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        var sql = "CREATE TABLE $TABLE_NAME(" +
                "ID INTEGER PRIMARY KEY," +
                "DESCRIPCION TEXT," +
                "CREDITOS INTEGER," +
                "ID_ESTUDIANTE INTEGER," +
                "FOREIGN KEY (ID_ESTUDIANTE) REFERENCES Estudiante(ID))"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertData(curso:Curso) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, curso.id)
        contentValues.put(COL_2, curso.descripcion)
        contentValues.put(COL_3, curso.creditos)
        contentValues.put(COL_4, curso.idEstudiante)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun updateData(curso:Curso):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, curso.id)
        contentValues.put(COL_2, curso.descripcion)
        contentValues.put(COL_3, curso.creditos)
        contentValues.put(COL_4, curso.idEstudiante)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(curso.id.toString()))
        return true
    }

    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))
    }

    @SuppressLint("Range")
    fun readCursos(): ArrayList<Curso> {
        val cursos = ArrayList<Curso>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_NAME", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id = 0
        var descripcion = ""
        var creditos = 0
        var idEstudiante = 0
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(COL_1))
                descripcion = cursor.getString(cursor.getColumnIndex(COL_2))
                creditos = cursor.getInt(cursor.getColumnIndex(COL_3))
                idEstudiante = cursor.getInt(cursor.getColumnIndex(COL_4))

                cursos.add(Curso(id, descripcion, creditos, idEstudiante))
                cursor.moveToNext()
            }
        }
        return cursos
    }

    @SuppressLint("Range")
    fun readEstudiante(id: String): ArrayList<Curso> {
        val cursos = ArrayList<Curso>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $TABLE_NAME WHERE $COL_1 = $id", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id = 0
        var descripcion = ""
        var creditos = 0
        var idEstudiante = 0
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(COL_1))
                descripcion = cursor.getString(cursor.getColumnIndex(COL_2))
                creditos = cursor.getInt(cursor.getColumnIndex(COL_3))
                idEstudiante = cursor.getInt(cursor.getColumnIndex(COL_4))

                cursos.add(Curso(id, descripcion, creditos, idEstudiante))
                cursor.moveToNext()
            }
        }
        return cursos
    }

    companion object {
        val DATABASE_NAME = "SQLite"
        val TABLE_NAME = "Curso"
        val COL_1 = "ID"
        val COL_2 = "DESCRIPCION"
        val COL_3 = "CREDITOS"
        val COL_4 = "ID_ESTUDIANTE"
    }

}