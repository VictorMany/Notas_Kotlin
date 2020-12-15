package com.example.appnotas.Modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase



class UsuarioDB(context: Context) {
    private val openHelper: DbOpenHelper = DbOpenHelper(context)
    private val database: SQLiteDatabase
    init {
        database = openHelper.writableDatabase
    }

    fun guardarUsuario(User: String, Password: String)
    {
        val values =  ContentValues()
        values.put("usuario", User)
        values.put("password", Password)
        database.insert("tblUsuarios",null,values)
        database.close()
    }
}