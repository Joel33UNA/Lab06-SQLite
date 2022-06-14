//package com.example.models
//
//class Estudiantes private constructor() {
//
//    private var estudiantes: ArrayList<Estudiante> = ArrayList<Estudiante>()
//
//
//    init{
//
//    }
//
//    private object HOLDER {
//        val INSTANCE = Estudiantes()
//    }
//
//    companion object {
//        val instance: Estudiantes by lazy {
//            HOLDER.INSTANCE
//        }
//    }
//
//    fun addPersona(estudiante: Estudiante){
//        estudiantes?.add(estudiante)
//    }
//
//    fun getPersona(nombre: String): Estudiante? {
//        for (p: Estudiante in estudiantes!!){
//            if(p.nombre.equals(nombre)){
//                return p;
//            }
//        }
//        return null;
//    }
//
//    fun getPersonas(): ArrayList<Estudiante>{
//        return this.estudiantes!!
//    }
//
//    fun login(user: String?, password: String?): Boolean{
//        for(p: Estudiante in estudiantes!!){
//            if(p.user.equals(user) && p.password.equals(password)){
//                return true
//            }
//        }
//        return false
//    }
//
//    fun loginP(user: String?, password: String?): Estudiante?{
//        for(p: Estudiante in estudiantes!!){
//            if(p.user.equals(user) && p.password.equals(password)){
//                return p
//            }
//        }
//        return null
//    }
//
//    fun deletePerson(position: Int){
//        estudiantes!!.removeAt(position)
//    }
//
//    fun editPerson(p: Estudiante, position: Int){
//        var aux = estudiantes!!.get(position)
//        aux.password = p.password
//        aux.nombre = p.nombre
//        aux.user = p.user
//    }
//}