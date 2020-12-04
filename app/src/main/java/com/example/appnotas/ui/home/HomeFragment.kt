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
import com.example.appnotas.DetalleNota
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Inicializar()
        Eventos()
    }

    fun Inicializar() {
        listaNotas = ArrayList()
        listaNotas.add(Nota("Ir al super", "Microsoft", "","","12/02/2020"))
        listaNotas.add(Nota("Larry Page", "Google", "","","12/02/2020"))
        listaNotas.add(Nota("Sergey Brin", "Google", "","","12/02/2020"))
        
        //TODO codigo adaptador
        val adaptadorNotas = AdaptadorNotas((activity as AppCompatActivity))
        lista.adapter = adaptadorNotas
    }

    fun Eventos() {

            lista.setOnItemClickListener { adapterView, view, position, id ->
                var intent = Intent(context, DetalleNota::class.java).apply {  //LE CAMBIé appContext por context
                    putExtra("titulo", listaNotas[position]._titulo)
                    putExtra("fecha", listaNotas[position]._fecha)
                }
                startActivity(intent)
            }
    }

    internal inner class AdaptadorNotas(context: Context) //ANTES ERA APPCOMPACTACTIVITY
        : ArrayAdapter<Nota>(context,0, listaNotas){
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_nota, parent, false)
            val currentPersona = getItem(position)
            inflater.lblTituloNota.text = currentPersona!!._titulo
            inflater.lblFecha.text = currentPersona!!._fecha
            return inflater
        }
    }
}