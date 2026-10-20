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

class MainActivity : AppCompatActivity() {

    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnIngresar: Button

    private lateinit var dbHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tvCrearCuenta = findViewById<TextView>(R.id.tvCrearCuenta)

        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPas)
        btnIngresar = findViewById(R.id.btnIngresar)

        dbHelper = DatabaseHelper(this)

        //evento para iniciar sesion
        btnIngresar.setOnClickListener {
            //Llamar al metodo para iniciar sesion
            iniciarSesion()
        }

        tvCrearCuenta.setOnClickListener{
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

    }

    //Metodo para iniciar sesion
    private fun iniciarSesion(){
        val correo = etCorreo.text.toString().trim()
        val pas = etPassword.text.toString().trim()

        if(correo.isEmpty() || pas.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        //llamar al metodo de inicio de sesion en la BD
        val resultado = dbHelper.iniciarSesion(correo, pas)

        //validar si el inicion de sesion fue correcto
        if (resultado){
            //el resultado es verdadero
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            val intent = Intent (this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }else{
            //el resultado es falso
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()

        }

    }
}