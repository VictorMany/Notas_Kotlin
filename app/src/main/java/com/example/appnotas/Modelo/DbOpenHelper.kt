package com.example.appnotas.Modelo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val nombreDB = "NotasBD.db"
val versionDB = 1


class DbOpenHelper(context: Context) :
    SQLiteOpenHelper(context, nombreDB,null, versionDB) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("""
            create table tblNotas (
                idNota integer primary key autoincrement,
                TituloNota text not null,
                DescripcionNota text not null,
                imgNota text not null,
                SeccionNota text not null,
                FechaNota text not null
            )
        """.trimIndent())

        db!!.execSQL("""
            create table tblUsuarios (
                idUsuario integer primary key autoincrement,
                usuario text not null,
                password text not null
            )
        """.trimIndent())
    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}