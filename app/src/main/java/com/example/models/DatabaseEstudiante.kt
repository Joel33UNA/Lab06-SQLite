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
class DatabaseEstudiante(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    /**
     * Our onCreate() method.
     * Called when the database is created for the first time. This is
     * where the creation of tables and the initial population of the tables
     * should happen.
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY,CLAVE TEXT,NOMBRE TEXT,APELLIDO TEXT,EDAD INTEGER)")
    }

    /**
     * Let's create Our onUpgrade method
     * Called when the database needs to be upgraded. The implementation should
     * use this method to drop tables, add tables, or do anything else it needs
     * to upgrade to the new schema version.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    @Throws(SQLiteConstraintException::class)
    fun insertData(estudiante:Estudiante) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, estudiante.id)
        contentValues.put(COL_2, estudiante.nombre)
        contentValues.put(COL_3, estudiante.clave)
        contentValues.put(COL_4, estudiante.apellido)
        contentValues.put(COL_5, estudiante.edad)
        db.insert(TABLE_NAME, null, contentValues)
    }

    /**
     * Let's create  a method to update a row with new field values.
     */
    fun updateData(estudiante:Estudiante):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, estudiante.id)
        contentValues.put(COL_2, estudiante.nombre)
        contentValues.put(COL_3, estudiante.clave)
        contentValues.put(COL_4, estudiante.apellido)
        contentValues.put(COL_5, estudiante.edad)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(estudiante.id.toString()))
        return true
    }

    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))
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
            cursor = db.rawQuery("select * from $TABLE_NAME", null)
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
                id = cursor.getInt(cursor.getColumnIndex(COL_1))
                nombre = cursor.getString(cursor.getColumnIndex(COL_2))
                clave = cursor.getString(cursor.getColumnIndex(COL_3))
                apellido = cursor.getString(cursor.getColumnIndex(COL_4))
                edad = cursor.getInt(cursor.getColumnIndex(COL_5))

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
            cursor = db.rawQuery("select * from $TABLE_NAME WHERE $COL_1 = $id", null)
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
                id = cursor.getInt(cursor.getColumnIndex(COL_1))
                nombre = cursor.getString(cursor.getColumnIndex(COL_2))
                clave = cursor.getString(cursor.getColumnIndex(COL_3))
                apellido = cursor.getString(cursor.getColumnIndex(COL_4))
                edad = cursor.getInt(cursor.getColumnIndex(COL_5))

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
            cursor = db.rawQuery("select * from $TABLE_NAME WHERE $COL_1 = $id AND $COL_3 = $clave", null)
        } catch (e: SQLiteException) {
            return null
        }
        if (cursor!!.moveToNext()) {
            var id = cursor.getInt(cursor.getColumnIndex(COL_1))
            var nombre = cursor.getString(cursor.getColumnIndex(COL_2))
            var clave = cursor.getString(cursor.getColumnIndex(COL_3))
            var apellido = cursor.getString(cursor.getColumnIndex(COL_4))
            var edad = cursor.getInt(cursor.getColumnIndex(COL_5))
            return Estudiante(id, nombre, clave, apellido, edad)
        }
        else return null
    }


    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */
    companion object {
        val DATABASE_NAME = "SQLite"
        val TABLE_NAME = "Estudiante"
        val COL_1 = "ID"
        val COL_2 = "NOMBRE"
        val COL_3 = "CLAVE"
        val COL_4 = "APELLIDO"
        val COL_5 = "EDAD"


    }
}

//end
