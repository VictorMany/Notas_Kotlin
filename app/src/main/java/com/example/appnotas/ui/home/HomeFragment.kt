package com.example.appnotas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.appnotas.Nota
import com.example.appnotas.R
import com.example.appnotas.ui.home.HomeViewModel
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Inicializar()
  
    }

    fun Inicializar() {
        listaNotas = ArrayList()
        listaNotas.add(Nota("Bill Gates", "Microsoft", "","",""))
        listaNotas.add(Nota("Larry Page", "Google", "","",""))
        listaNotas.add(Nota("Sergey Brin", "Google", "","",""))
        
        //TODO codigo adaptador
        val adaptadorNotas = AdaptadorNotas(this)
        lista.adapter = adaptadorNotas
    }





    internal inner class AdaptadorNotas(context: HomeFragment) //ANTES ERA APPCOMPACTACTIVITY
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