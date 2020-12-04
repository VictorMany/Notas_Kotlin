package com.example.appnotas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar



class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var listaNotas: ArrayList<Nota>
    val lista: ListView = findViewById(R.id.lista)
    val lblTitulo: TextView = findViewById(R.id.lblTituloNota)
    val lblFecha: TextView = findViewById(R.id.lblFecha)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        setContentView(R.layout.content_main)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        Inicializar()
        Eventos()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun Inicializar() {
        listaNotas = ArrayList()
        listaNotas.add(Nota("Comprar despensa", "Naranjas, coca, aceite", "", "Viajes", "2/12/2020"))
        listaNotas.add(Nota("Pagar la luz", "Naranjas, coca, aceite", "", "Viajes", "2/12/2020"))
        listaNotas.add(Nota("Ir a misa", "Naranjas, coca, aceite", "", "Viajes", "2/12/2020"))

        //TODO codigo adaptador
        val adaptadorPersonas = AdaptadorNotas(this)
        lista.adapter = adaptadorPersonas
    }


    fun Eventos() {
        lista.setOnItemClickListener { adapterView, view, position, id ->
            var intent = Intent(applicationContext, DetalleNota::class.java).apply {
                putExtra("titulo", listaNotas[position]._titulo)
                putExtra("fecha", listaNotas[position]._fecha)
            }
            startActivity(intent)
        }

    }

    internal inner class AdaptadorNotas(context: AppCompatActivity)
        : ArrayAdapter<Nota>(context,0,listaNotas){
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_nota, parent, false)
            val currentNota = getItem(position)
           // inflater.lblTitulo.text = currentNota!!._titulo
           // inflater.lblFecha.text = currentNota!!._fecha
            return inflater
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}