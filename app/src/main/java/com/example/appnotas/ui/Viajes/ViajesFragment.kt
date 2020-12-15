package com.example.appnotas.ui.Viajes

import android.content.Context
import android.content.Intent
import android.database.CursorWindow
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appnotas.DetalleNota
import com.example.appnotas.Modelo.NotasBD
import com.example.appnotas.Nota
import com.example.appnotas.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_nota.view.*
import java.lang.reflect.Field

class ViajesFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_viajes, container, false)
        //val textView: TextView = root.findViewById(R.id.text_gallery)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            e.printStackTrace()
        }
        LlenarInformacion()
    }

    fun LlenarInformacion(){
        val datasource = NotasBD(activity as AppCompatActivity)
        val registros =  ArrayList<Nota>()
        val cursor =  datasource.consultarNotas("Viajes")
        while (cursor.moveToNext()){
            val columnas = Nota(
                cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)
            )
            registros.add(columnas)
        }

        val adaptador =  AdaptadorNotas(activity as AppCompatActivity, registros)
        lista.adapter =  adaptador
        lista.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as Nota
            var intent  =  Intent(context, DetalleNota::class.java).apply {
                putExtra("id", item._id)
                putExtra("seccion", item._seccion)
            }
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        this.LlenarInformacion()
    }

    override fun onResume() {
        super.onResume()
        this.LlenarInformacion()
    }

    internal  class AdaptadorNotas(context: Context, datos: List<Nota>):
        ArrayAdapter<Nota>(context, R.layout.layout_nota, datos)
    {
        var _datos: List<Nota>
        init {
            _datos =  datos
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater =  convertView ?: LayoutInflater.from(context).inflate(
                R.layout.layout_nota, parent,
                false
            )
            val currentPersona = getItem(position)
            inflater.lblTituloNota.text = currentPersona!!._titulo
            inflater.lblFecha.text = currentPersona!!._fecha
            return inflater
        }
    }
}