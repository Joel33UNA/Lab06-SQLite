package com.example.models

class Curso : java.io.Serializable{
    var id:Int = 0
    var descripcion:String = ""
    var creditos: Int = 0
    var idEstudiante = 0

    internal constructor(id:Int, descripcion:String, creditos:Int, idEstudiante:Int){
        this.id = id
        this.descripcion = descripcion
        this.creditos = creditos
        this.idEstudiante = idEstudiante
    }
}