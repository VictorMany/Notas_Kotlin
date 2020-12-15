package com.example.appnotas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appnotas.MainActivity
import com.example.appnotas.Modelo.NotasBD
import com.example.appnotas.Modelo.UsuarioDB
import com.example.appnotas.R
import kotlinx.android.synthetic.main.activity_detalle_nota.*
import kotlinx.android.synthetic.main.activity_registrar.*

class Registrar : AppCompatActivity() {
    private lateinit var datasource: UsuarioDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        datasource = UsuarioDB(this)
    }

    fun GuardarUsuario (view: View){
        // TODO se realizara una inserción
        datasource.guardarUsuario(
            editTextTextEmailAddress.text.toString(),
            editTextTextPassword.text.toString()
        )
        Toast.makeText(
            applicationContext, "Se guardo correctamente",
            Toast.LENGTH_SHORT
        ).show()

        var intent = Intent(this, Login::class.java) //getClass()
        startActivity(intent)
    }

    fun Cancelar(view: View){
        var intent = Intent(this,Login::class.java) //getClass()
        startActivity(intent)
    }
}