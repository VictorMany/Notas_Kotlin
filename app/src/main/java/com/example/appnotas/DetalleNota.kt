package com.example.appnotas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.CursorWindow
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.example.appnotas.Modelo.ImageConverter
import com.example.appnotas.Modelo.NotasBD
import com.example.appnotas.ui.Todaslasnotas.HomeFragment

import kotlinx.android.synthetic.main.activity_detalle_nota.*
import java.lang.reflect.Field
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class DetalleNota : AppCompatActivity() {
    var img : Bitmap? = null
    var bazar:String? = ""
    private lateinit var datasource:NotasBD
    lateinit var spinner: Spinner

    private var id = 0
    private var titulo = ""
    private var seccion = ""
    private var descripcion = ""
    private var fecha = "12-02-2020"
    private var seccion2 = ""


    private var positionEstado = -1

    private val imageConverter: ImageConverter = ImageConverter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_nota)
        datasource = NotasBD(this)
        val extras = this.intent.extras

        //TODO Obtner la fecha actual

        fecha = DateFormat.format("dd-MM-yyyy", Date()).toString()

        //TODO hacer que el cursor de la consulta soporte una tamaño mayor por el uso de Base64  to Bitmap
        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (extras != null) {
            println(extras.getString("seccion") + "ZZZZZZZ")
            id = extras.getInt("id")

            seccion = extras.getString("seccion")!!
            when (seccion) {
                "Tareas" -> positionEstado = 0
                "Viajes" -> positionEstado = 1
                "Personal" -> positionEstado = 2
            }
            obtenerNota(id);
        }

        spinner = findViewById(R.id.spinner_seccion)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.seccionArray_strings,
            android.R.layout.simple_spinner_item
        )


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner_seccion?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (seccion==""){
                    val civil: String = parent?.getItemAtPosition(position).toString()
                    seccion2 = civil
                } else {
                    if(positionEstado == 1 || positionEstado == 2 || positionEstado == 0){
                        parent?.setSelection(positionEstado)
                        positionEstado = - 1
                    }
                    else{
                        parent?.setSelection(position)
                    }
                    val option: String = parent?.getItemAtPosition(position).toString()
                    seccion2 = option
                }
            }
        }
    }


    fun obtenerNota(id: Int):ArrayList<Nota>{
        val datasource = NotasBD(this)
        val registros =  ArrayList<Nota>()
        val cursor =  datasource.consultarNotas(id)
        while (cursor.moveToNext()){
            val columnas = Nota(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            )
            registros.add(columnas)
        }
        txtTitulo.setText(registros[0]._titulo)
        txtDetalleNota.setText(registros[0]._descripcion)

        image_note.setImageBitmap(imageConverter.bitmap(registros[0]._imagen))
        bazar = registros[0]._imagen
        /////////////////////////////////////////////////////////////////////////////
        return registros
    }

    fun TomarFoto(view: View){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {

            ImageProvider(this@DetalleNota).getImage(ImageSource.CAMERA){ bitmap ->
                img = bitmap
                image_note.setImageBitmap(bitmap)
                bazar = imageConverter.base64(img!!);
            }

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                42424
            )
        }
    }

    fun Eliminar(view: View){
        //TODO Preguntar al usuario si en verdad lo desea eliminar
        val builder =  AlertDialog.Builder(this@DetalleNota)
        builder.setMessage("¿Deseas eliminar esta nota?")
            .setTitle("Eliminar")
            .setPositiveButton("Aceptar"){ dialog, lis ->
                if(datasource.eliminarNota(id)){
                    Toast.makeText(
                        applicationContext, "Se realizo correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    txtTitulo.setText("")
                    txtDetalleNota.setText("")
                    id = 0
                }
            }
            .setNegativeButton("Cancelar") { dialog, lis ->
                Toast.makeText(
                    applicationContext, "Se cancelo la eliminación",
                    Toast.LENGTH_SHORT
                ).show()
            }
        builder.create().show()
    }

    fun GuardarNota(view: View){
        if(id != 0){
            //TODO se realizara una modificación

            //print(imgConvertida);
            datasource.modificarNota(
                id,
                txtTitulo.text.toString(),
                txtDetalleNota.text.toString(),
                bazar!!,
                seccion2,
                fecha
            )
            Toast.makeText(
                applicationContext, "Se editó correctamente",
                Toast.LENGTH_SHORT
            ).show()
        }else {
            // TODO se realizara una inserción
            var imgModificada  = imageConverter.base64(img!!)
            datasource.guardarNota(
                txtTitulo.text.toString(),
                txtDetalleNota.text.toString(),
                imgModificada!!,
                seccion2,
                fecha
            )
            println("ESTA ES LA SECCION $seccion2")
            Toast.makeText(
                applicationContext, "Se guardo correctamente",
                Toast.LENGTH_SHORT
            ).show()
        }
        var intent = Intent(this,HomeFragment::class.java) //getClass()
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 42424){
            ImageProvider(this@DetalleNota).getImage(ImageSource.CAMERA){ bitmap ->
                img = bitmap
                image_note.setImageBitmap(bitmap)
            }
        }
    }
}