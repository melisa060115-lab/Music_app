package com.example.music_app4b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroActivity : AppCompatActivity() {

    //SE DECLARAR LAS CONEXIONES
    private lateinit var etNombre : EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegistrar: Button

    //crar instancia de DatabaseHelper
    private lateinit var dbHelper: DatabaseHelper


    //METODO PRINCIPAL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        //inicializar los objetos de los componentes
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPas)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        //ininiar la base de datos en un objetos
        dbHelper = DatabaseHelper(this)

        //evento para registrar al usuario
        btnRegistrar.setOnClickListener {
            //registrar al usuario
            registrarUsuario()

        }
    }

    //METODOS POR AQUI
    private fun registrarUsuario(){
        //variables para poner los textos
        val nombre= etNombre.text.toString().trim()
        val correo= etCorreo.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        //Variable que guarda el resultado del registro y llama a la BD
        val registro = dbHelper.registrarUsuario(nombre, correo, password)

        //validar que el resultado de la insercion sea verdadero o falso
        if (registro){
            //si el registro es correcto
            Toast.makeText(this, "El registro fue exitoso", Toast.LENGTH_SHORT).show()
            //cierra la pantalla actual y regresa a la pantalla anterior
            //Prueba de git
        }else{
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()

        }



    }

}