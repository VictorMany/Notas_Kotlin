package com.example.appnotas.Modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class NotasBD(context: Context) {

    private val openHelper: DbOpenHelper = DbOpenHelper(context)
    private val database: SQLiteDatabase

    init {
        database = openHelper.writableDatabase
    }

    fun consultarNotas(): Cursor {
        return database.rawQuery("""
            select idNota, TituloNota,DescripcionNota, imgNota, SeccionNota, FechaNota
              from tblNotas
        """.trimIndent(),null)
        database.close()
    }

    fun consultarNotas(idNota:Int):Cursor{
        return database.rawQuery("""
             select idNota, TituloNota, DescripcionNota, imgNota, SeccionNota, FechaNota
                     from tblNotas 
                where idNota =$idNota
        """.trimIndent(),null)
        database.close()
    }

    fun consultarNotas(seccion: String): Cursor {
        return database.rawQuery("""
            select idNota, TituloNota, DescripcionNota, imgNota, SeccionNota, FechaNota
                from tblNotas where SeccionNota = "$seccion"
           
        """.trimIndent(),null)
        database.close()
    }

    fun guardarNota(TituloNota: String, DescripcionNota: String, imgNota: String, SeccionNota:String, FechaNota: String)
    {
        val values =  ContentValues()
        values.put("TituloNota", TituloNota)
        values.put("DescripcionNota",DescripcionNota)
        values.put("imgNota", imgNota)
        values.put("SeccionNota", SeccionNota)
        values.put("FechaNota", FechaNota)
        database.insert("tblNotas",null,values)
        database.close()
    }


    fun modificarNota(id: Int, TituloNota: String, DescripcionNota: String, imgNota: String, SeccionNota:String, FechaNota: String)
    {
        val values =  ContentValues()
        values.put("TituloNota",TituloNota)
        values.put("DescripcionNota",DescripcionNota)
        values.put("imgNota", imgNota)
        values.put("SeccionNota", SeccionNota)
        values.put("FechaNota", FechaNota)
        val whereArgs  = arrayOf(id.toString())

        database.update("tblNotas",values,"idNota=?",whereArgs)
        database.close()
    }
    fun eliminarNota(id: Int):Boolean{
        val whereArgs = arrayOf(id.toString())
        try {
            database.delete("tblNotas","idNota=?",whereArgs)
            return true
        }catch (ex:Exception){
            return false
        }finally {
            database.close()
        }
    }
}