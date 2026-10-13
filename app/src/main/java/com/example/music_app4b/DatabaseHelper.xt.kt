package com.example.music_app4b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//crear clase DataHelper que hereda de la clae SQLite
class DatabaseHelper(context: Context) : SQLiteOpenHelper (
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
){
    //Datos de la base de datos
    companion object{

        //informacion de la BD
        private const val DATABASE_NAME = "musica.db"
        private const val DATABASE_VERSION = 2

        //informacion de las tablas en la BD
        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_ID = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_CORREO = "correo"
        private const val COL_PASSWORD = "password"

    }
    //METODO ONCREATE
    override fun onCreate(db: SQLiteDatabase?) {
        //CREAR LA TABLA

        val crearTablaUsuarios = """
            CREATE TABLE $TABLE_USUARIOS(
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NOMBRE TEXT NOT NULL,
            $COL_CORREO TEXT NOT NULL,
            $COL_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        //CREAR TABLA DE USUARIOS
        db?.execSQL(crearTablaUsuarios)
    }

    //METODO ONUPGRADE
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    //METODO PARA REGISTRAR UN USUARIO (INSERT)
    fun registrarUsuario(nombre : String, correo : String, password : String) : Boolean {
        //crear variable para escribir en la BD
        val db = writableDatabase

        //insertar los datos
        val datos = ContentValues().apply{
            put(COL_NOMBRE, nombre)
            put(COL_CORREO, correo)
            put(COL_PASSWORD, password)
        }

        //Ejecutar insercion de datos
        val resultado = db.insert(TABLE_USUARIOS, null, datos)

        //cerrar conexion de la BD
        db.close()

        //regresar el valor obtenido de la inserción (verdadero o falso)
        return  resultado != -1L
    }
    //Metodo para iniciar sesion (consulta)
    fun iniciarSesion(correo: String, password: String) : Boolean{

        //variabñe que lee la base de datos
        val db = readableDatabase

        //crear consulta
        val query_sql = """
            SELECT * FROM $TABLE_USUARIOS
            WHERE $COL_CORREO = ? AND $COL_PASSWORD = ?
        """.trimIndent()

        //CREAR UN CURSOR QUE PERMITA RECORRER TODOS LOS REGISTROS
        val cursor = db.rawQuery(
            query_sql, arrayOf(correo, password)
        )

        //verifiar cuantos resultado encuentra el cursor
        val usuarioEncontrado = cursor.count > 0

        cursor.close()
        db.close()

        return usuarioEncontrado

    }

}