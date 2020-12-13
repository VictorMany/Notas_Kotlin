package com.example.appnotas.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.appnotas.DetalleNota
import com.example.appnotas.Modelo.ImageConverter
import com.example.appnotas.Modelo.NotasBD
import com.example.appnotas.Nota
import com.example.appnotas.R
import com.example.appnotas.ui.gallery.GalleryFragment
import com.example.appnotas.ui.home.HomeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_nota.view.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    lateinit var listaNotas: ArrayList<Nota>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_home, container, false)
        return view;
    }

    override fun onPause() {
        super.onPause()
        this.LlenarInformacion()
    }
    fun LlenarInformacion(){

        val datasource = NotasBD(this)
        val registros =  ArrayList<Nota>()
        val cursor =  datasource.consultarNotas()
        while (cursor.moveToNext()){
            val columnas = Nota(
                cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)
            )
            registros.add(columnas)
        }
        val adaptador =  AdaptadorNotas(this, registros)

        listaNotas.adapter =  adaptador

        listaNotas.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as Personas
            var intent  =  Intent(this@HomeFragment, activity_detalle_nota::class.java).apply {
                putExtra("id", item._id)
                putExtra("Ti", item._edoCivil) //esta pendiente este pedo
                putExtra("ratingPersona", item._ratingPersona)
            }
            //TODO: Antigua forma que se usa en JAVA
            //intent.putExtra("id",item._idPersona);
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        this.LlenarInformacion()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Inicializar()
        Eventos()
    }

    fun Inicializar() {
        listaNotas = ArrayList()
        //listaNotas.add(Nota("Ir al super", "Microsoft", "","","12/02/2020"))
        //listaNotas.add(Nota("Larry Page", "Google", "","","12/02/2020"))
        //listaNotas.add(Nota("Sergey Brin", "Google", "","","12/02/2020"))
        
        //TODO codigo adaptador
        val adaptadorNotas = AdaptadorNotas((activity as AppCompatActivity))
        lista.adapter = adaptadorNotas
    }

    fun Eventos() {

            lista.setOnItemClickListener { adapterView, view, position, id ->
                var intent = Intent(context, DetalleNota::class.java).apply {  //LE CAMBIé appContext por context
                    putExtra("titulo", listaNotas[position]._titulo)
                    putExtra("fecha", listaNotas[position]._fecha)// tmb esto esta pendiente
                }
                startActivity(intent)
            }
    }


    //TODO internal class jajaj miau
    internal inner class AdaptadorNotas(context: Context, datos: List<Nota>) //ANTES ERA APPCOMPACTACTIVITY
        : ArrayAdapter<Nota>(context, R.layout.activity_detalle_nota, listaNotas){
        private val imageConverter: ImageConverter = ImageConverter()
        var _datos: List<Nota>
        init {
            _datos =  datos
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_nota, parent, false)

            val currentNota = getItem(position)
            inflater.lblTituloNota.text = currentNota!!._titulo
            inflater.lblFecha.text = currentNota!!._fecha
            return inflater
        }
    }



}