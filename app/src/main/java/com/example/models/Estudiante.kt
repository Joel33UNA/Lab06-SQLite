package com.example.models

import java.io.Serializable

class Estudiante : Serializable {
    var id:Int = 0
    var clave:String = ""
    var nombre:String = ""
    var apellido:String = ""
    var edad: Int = 0

    internal constructor(id:Int, nombre:String, clave:String, apellido:String, edad:Int){
        this.id = id
        this.nombre = nombre
        this.clave = clave
        this.apellido = apellido
        this.edad = edad
    }
}