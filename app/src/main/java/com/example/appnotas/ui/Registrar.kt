package com.example.appnotas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appnotas.Modelo.NotasBD
import com.example.appnotas.R
import kotlinx.android.synthetic.main.activity_registrar.*


class Registrar : AppCompatActivity() {
    private lateinit var datasource:NotasBD
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
    }

    fun GuardarPersona(view: View){

        if(id != 0){
            //TODO se realizara una modificación
            datasource.modificarNota(id,txtRegistar.text.toString(),txtPass.text.toString(),"", "", "")
            Toast.makeText(applicationContext,"Se editó correctamente",
                Toast.LENGTH_SHORT).show()

        }else {
            // TODO se realizara una inserción
            datasource.guardarNota(txtRegistar.text.toString(),txtPass.text.toString(), "", "", "")
            Toast.makeText(applicationContext,"Se guardo correctamente",
                Toast.LENGTH_SHORT).show()
        }

    }

}